package me.dhlee.model

data class HttpResponseLog(
    val traceId: String,
    val status: Int,
    val method: String,
    val path: String,
    val headers: Map<String, String>,
    val body: String,
    val contentType: String?,
)
