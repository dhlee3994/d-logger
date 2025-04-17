package me.dhlee.filter

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.dhlee.common.HttpLoggingConstants
import org.springframework.web.servlet.HandlerInterceptor

class HttpLoggingInterceptor : HandlerInterceptor {

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        if (ex != null) {
            request.setAttribute(HttpLoggingConstants.DLOGGER_LOG_MARKER_KEY, "");
        }
    }
}