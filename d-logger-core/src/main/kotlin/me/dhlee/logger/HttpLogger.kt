package me.dhlee.logger

import me.dhlee.format.HttpLogFormatter
import me.dhlee.model.HttpRequestLog
import me.dhlee.model.HttpResponseLog
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HttpLogger(
    private val formatter: HttpLogFormatter
){
    private val log: Logger = LoggerFactory.getLogger(HttpLogger::class.java)

    fun logRequest(request: HttpRequestLog) {
        log.info(formatter.formatRequest(request))
    }

    fun logResponse(response: HttpResponseLog) {
        log.info(formatter.formatResponse(response))
    }
}