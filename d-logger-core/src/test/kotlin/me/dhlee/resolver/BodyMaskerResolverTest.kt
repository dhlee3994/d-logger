package me.dhlee.resolver

import me.dhlee.mask.FormUrlEncodedBodyMasker
import me.dhlee.mask.JsonBodyMasker
import me.dhlee.mask.NoOpBodyMasker
import me.dhlee.properties.HttpLoggingProperties
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BodyMaskerResolverTest {

    private val properties = HttpLoggingProperties(
        includeBody = true,
        maskHeaders = emptySet(),
        maskBody = setOf("password", "token")
    )
    private val resolver = BodyMaskerResolver(properties)

    @Test
    fun `Content-Type이 application-json인 경우 JsonBodyMasker를 반환한다`() {
        val contentType = "application/json"

        val masker = resolver.resolve(contentType)

        assertTrue(masker is JsonBodyMasker)
    }

    @Test
    fun `Content-Type이 application-x-www-form-urlencoded인 경우 FormUrlEncodedBodyMasker를 반환한다`() {
        val contentType = "application/x-www-form-urlencoded"

        val masker = resolver.resolve(contentType)

        assertTrue(masker is FormUrlEncodedBodyMasker)
    }

    @Test
    fun `Content-Type이 없으면 noOpMasker를 반환한다`() {
        val masker = resolver.resolve(null)

        assertTrue(masker is NoOpBodyMasker)
    }

    @Test
    fun `지원되지 않은 Content-Type이면 noOpMasker를 반환한다`() {
        val contentType = "text/plain"

        val masker = resolver.resolve(contentType)

        assertTrue(masker is NoOpBodyMasker)
    }
}