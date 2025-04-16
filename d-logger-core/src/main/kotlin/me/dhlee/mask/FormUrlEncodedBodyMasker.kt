package me.dhlee.mask

import me.dhlee.properties.HttpLoggingProperties
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class FormUrlEncodedBodyMasker(
    props: HttpLoggingProperties,
) : BodyMasker {
    private val keysToMask: Set<String> = props.maskBody.map { it.lowercase() }.toSet()

    override fun mask(body: String?): String {
        if (body.isNullOrBlank()) return ""

        return body.split("&").joinToString("&") { pair ->
            val (rawKey, rawValue) = pair.split("=", limit = 2).let {
                when (it.size) {
                    2 -> it[0] to it[1]
                    1 -> it[0] to ""
                    else -> "" to ""
                }
            }

            val key = URLDecoder.decode(rawKey, StandardCharsets.UTF_8)
            val value = URLDecoder.decode(rawValue, StandardCharsets.UTF_8)

            if (key.lowercase() in keysToMask) "$key=****" else "$key=$value"
        }
    }
}