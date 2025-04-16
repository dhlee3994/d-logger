package me.dhlee.mask

import com.fasterxml.jackson.databind.ObjectMapper
import me.dhlee.properties.HttpLoggingProperties
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class JsonBodyMaskerTest {

    private val keyToMask = "email"

    private val masker = JsonBodyMasker(
        HttpLoggingProperties(
            maskBody = setOf(keyToMask),
        )
    )

    private val objectMapper = ObjectMapper()

    @Test
    fun `단일 키를 마스킹할 수 있다`() {
        val input = """{"$keyToMask": "test@test.com"}""".trimIndent()

        val result = masker.mask(input)

        val json = objectMapper.readTree(result)
        assertTrue(json.get(keyToMask).asText() == "****")
    }
    
    @Test
    fun `중첩 객체안에 있는 키를 마스킹할 수 있다`() {
        val input = """{"data": {"$keyToMask": "test@test.com"}}""".trimIndent()
        
        val result = masker.mask(input)

        val json = objectMapper.readTree(result)
        assertTrue(json["data"].get(keyToMask).asText() == "****")
    }
    
    @Test
    fun `배열 안에 있는 키를 마스킹할 수 있다`() {
        val input = """{"list": [{"email": "testA@test.com"}, {"email": "testB@test.com"}]}""".trimIndent()

        val result = masker.mask(input)

        val json = objectMapper.readTree(result)
        assertTrue(json["list"][0]["email"].asText() == "****")
        assertTrue(json["list"][1]["email"].asText() == "****")
    }

    @Test
    fun `마스킹 대상 키의 대소문자와 관계없이 마스킹할 수 있다`() {
        val input = """{"${keyToMask.uppercase()}": "test@test.com"}""".trimIndent()

        val result = masker.mask(input)

        val json = objectMapper.readTree(result)
        assertTrue(json.get(keyToMask.uppercase()).asText() == "****")
    }

    @Test
    fun `마스킹 대상 키와 동일한 문자열을 가진 값은 마스킹되지 않는다`() {
        val input = """{"name": "$keyToMask"}""".trimIndent()

        val result = masker.mask(input)

        val json = objectMapper.readTree(result)
        assertTrue(json.get("name").asText() == keyToMask)
    }

    @Test
    fun `비정상적인 JSON은 원본 값을 반환한다`() {
        val input = """{"${keyToMask.uppercase()}": "test@test.com"]""".trimIndent()

        val masked = masker.mask(input)

        assertTrue(masked == input)
    }
    
    @Test
    fun `null 또는 빈 문자열인 경우 빈 문자열을 반환한다`() {
        assertTrue(masker.mask("") == "")
        assertTrue(masker.mask("  ") == "")
        assertTrue(masker.mask(null) == "")
    }
}