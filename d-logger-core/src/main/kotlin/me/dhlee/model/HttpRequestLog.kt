package me.dhlee.model

data class HttpRequestLog(
    val traceId: String,
    val method: String,
    val path: String,
    val headers: Map<String, String>,
    val body: String,
    val contentType: String?,
)