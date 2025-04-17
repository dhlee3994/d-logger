package me.dhlee.properties

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SpringHttpLoggingPropertiesTest {

    @Test
    fun `HttpLoggingProperties 객체로 변환할 수 있다`() {
        val includeBody = true
        val maskHeaders = setOf("Authorization")
        val maskBody = setOf("password")

        val properties = SpringHttpLoggingProperties(
            includeBody = includeBody,
            maskHeaders = maskHeaders,
            maskBody = maskBody
        )

        val result = properties.toCoreProperties()

        assertNotNull(result)
        assertEquals(includeBody, result.includeBody)
        assertEquals(maskHeaders, result.maskHeaders)
        assertEquals(maskBody, result.maskBody)
    }
}