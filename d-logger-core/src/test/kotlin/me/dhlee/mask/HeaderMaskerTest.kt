package me.dhlee.mask

import me.dhlee.properties.HttpLoggingProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HeaderMaskerTest {

    private val keyToMask = "Authorization"
    
    private val masker = HeaderMasker(
        HttpLoggingProperties(
            maskHeaders = setOf(keyToMask),
        )
    )

    @Test
    fun `마스킹 대상 헤더의 값을 헤더의 대소문자와 관계없이 마스킹할 수 있다`() {
        val key = keyToMask.uppercase()
        val headers = mapOf(
            key to "Bearer token123",
        )
        
        val result = masker.mask(headers)
        
        assertEquals("****", result[key])
    }
    
    @Test
    fun `마스킹 대상이 없으면 원본 값을 반환한다`() {
        val headers = mapOf(
            "Content-Type" to "application/json"
        )
        
        val result = masker.mask(headers)
        
        assertEquals("application/json", result["Content-Type"])
    }
    
    @Test
    fun `헤더가 비어있으면 빈 결과를 반환한다`() {
        val headers = emptyMap<String, String>()
        
        val result = masker.mask(headers)
        
        assertTrue(result.isEmpty())
    }
}