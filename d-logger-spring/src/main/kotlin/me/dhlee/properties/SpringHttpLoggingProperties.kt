package me.dhlee.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "http-logging")
data class SpringHttpLoggingProperties(
    var prod: Boolean = true,
    var includeBody: Boolean = true,
    var includePaths: Set<String> = emptySet(),
    var excludePaths: Set<String> = emptySet(),
    var maskHeaders: Set<String> = emptySet(),
    var maskBody: Set<String> = emptySet(),
) {
    fun toCoreProperties(): HttpLoggingProperties {
        return HttpLoggingProperties(
            includeBody = includeBody,
            maskHeaders = maskHeaders,
            maskBody = maskBody
        )
    }
}