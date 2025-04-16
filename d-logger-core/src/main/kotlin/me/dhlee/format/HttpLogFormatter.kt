package me.dhlee.format

import me.dhlee.model.HttpRequestLog
import me.dhlee.model.HttpResponseLog

interface HttpLogFormatter {
    fun formatRequest(request: HttpRequestLog): String

    fun formatResponse(response: HttpResponseLog): String
}