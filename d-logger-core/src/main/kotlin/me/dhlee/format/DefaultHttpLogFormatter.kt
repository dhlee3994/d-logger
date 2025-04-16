package me.dhlee.format

import me.dhlee.mask.HeaderMasker
import me.dhlee.model.HttpRequestLog
import me.dhlee.model.HttpResponseLog
import me.dhlee.properties.HttpLoggingProperties
import me.dhlee.resolver.BodyMaskerResolver

class DefaultHttpLogFormatter(
    private val properties: HttpLoggingProperties
) : HttpLogFormatter {

    private val headerMasker: HeaderMasker = HeaderMasker(properties)
    private val bodyMaskerResolver = BodyMaskerResolver(properties)

    override fun formatRequest(request: HttpRequestLog): String {
        val maskedHeader = headerMasker.mask(request.headers)

        val bodyMasker = bodyMaskerResolver.resolve(request.contentType)
        val maskedBody = if (properties.includeBody) {
            bodyMasker.mask(request.body)
        } else {
            "[Body omitted]"
        }

        return """
            |[REQUEST - ${request.traceId}]
            |${request.method} ${request.path}
            |${maskedHeader.entries.joinToString("\n") { "${it.key}: ${it.value}" }}
            |
            |$maskedBody
            
        """.trimMargin()
    }

    override fun formatResponse(response: HttpResponseLog): String {
        val maskedHeader = headerMasker.mask(response.headers)

        val bodyMasker = bodyMaskerResolver.resolve(response.contentType)
        val maskedBody = if (properties.includeBody) {
            bodyMasker.mask(response.body)
        } else {
            "[Body omitted]"
        }

        return """
            |[RESPONSE - ${response.traceId}]
            |${response.status} ${response.method} ${response.path}
            |${maskedHeader.entries.joinToString("\n") { "${it.key}: ${it.value}" }}
            |
            |$maskedBody
            
        """.trimMargin()
    }
}
