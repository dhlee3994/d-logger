package me.dhlee.mask

import me.dhlee.properties.HttpLoggingProperties

class HeaderMasker(
    props: HttpLoggingProperties,
) {
    private val keysToMask: Set<String> = props.maskHeaders.map { it.lowercase() }.toSet()

    fun mask(headers: Map<String, String>): Map<String, String> {
        return headers.map { (key, value) ->
            val masked = key.lowercase() in keysToMask
            if (masked) key to "****" else key to value
        }.toMap()
    }
}