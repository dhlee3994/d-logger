package me.dhlee.resolver

import me.dhlee.mask.BodyMasker
import me.dhlee.mask.FormUrlEncodedBodyMasker
import me.dhlee.mask.JsonBodyMasker
import me.dhlee.mask.NoOpBodyMasker
import me.dhlee.properties.HttpLoggingProperties

class BodyMaskerResolver(
    properties: HttpLoggingProperties
) {
    private val noOpMasker = NoOpBodyMasker()
    private val jsonMasker = JsonBodyMasker(properties)
    private val formMasker = FormUrlEncodedBodyMasker(properties)

    fun resolve(contentType: String?): BodyMasker {
        return when {
            contentType == null -> noOpMasker
            contentType.contains("application/json") -> jsonMasker
            contentType.contains("application/x-www-form-urlencoded") -> formMasker
            else -> noOpMasker
        }
    }
}