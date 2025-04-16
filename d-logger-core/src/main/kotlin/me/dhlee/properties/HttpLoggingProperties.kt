package me.dhlee.properties

data class HttpLoggingProperties(
    val includeBody: Boolean = true,
    val maskHeaders: Set<String> = setOf(),
    val maskBody: Set<String> = setOf(),
)