package me.dhlee.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.dhlee.common.HttpLoggingConstants
import me.dhlee.context.TraceIdProvider
import me.dhlee.logger.HttpLogger
import me.dhlee.model.HttpRequestLog
import me.dhlee.model.HttpResponseLog
import me.dhlee.properties.SpringHttpLoggingProperties
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.net.URLDecoder

class HttpLoggingFilter(
    private val props: SpringHttpLoggingProperties,
    private val httpLogger: HttpLogger,
    private val pathFilter: PathFilter
) : OncePerRequestFilter() {
    override fun shouldNotFilterAsyncDispatch(): Boolean = false
    override fun shouldNotFilterErrorDispatch(): Boolean = false
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val wrappedRequest = ContentCachingRequestWrapper(request)
        val wrappedResponse = ContentCachingResponseWrapper(response)
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse)
        } finally {
            logIfNecessary(wrappedRequest, wrappedResponse)
            wrappedResponse.copyBodyToResponse()
            TraceIdProvider.clear()
        }
    }

    private fun logIfNecessary(
        request: ContentCachingRequestWrapper,
        response: ContentCachingResponseWrapper
    ) {
        if (props.prod && request.getAttribute(HttpLoggingConstants.DLOGGER_LOG_MARKER_KEY) == null) {
            return
        }

        val method = request.method
        val path = extractPath(request)
        if (!pathFilter.shouldLog(path)) {
            return
        }

        val traceId = TraceIdProvider.getOrCreate()

        val requestLog = HttpRequestLog(
            traceId = traceId,
            method = method,
            path = path,
            headers = extractHeaders(request),
            body = getRequestBody(request),
            contentType = request.contentType,
        )

        val responseLog = HttpResponseLog(
            traceId = traceId,
            status = response.status,
            method = method,
            path = path,
            headers = extractHeaders(response),
            body = getResponseBody(response),
            contentType = response.contentType,
        )

        httpLogger.logRequest(requestLog)
        httpLogger.logResponse(responseLog)
    }

    private fun extractPath(request: ContentCachingRequestWrapper) =
        request.requestURI + request.queryString?.let { URLDecoder.decode(it, Charsets.UTF_8) }.orEmpty()

    private fun extractHeaders(request: ContentCachingRequestWrapper): Map<String, String> {
        return request.headerNames.toList().associateWith { name ->
            request.getHeader(name)
        }
    }

    private fun extractHeaders(response: ContentCachingResponseWrapper): Map<String, String> {
        return response.headerNames.toList().associateWith { name ->
            response.getHeader(name) ?: ""
        }
    }

    private fun getRequestBody(request: ContentCachingRequestWrapper): String {
        return toString(request.contentAsByteArray)
    }

    private fun getResponseBody(response: ContentCachingResponseWrapper): String {
        return toString(response.contentAsByteArray)
    }

    private fun toString(bytes: ByteArray): String = String(bytes, Charsets.UTF_8)
}