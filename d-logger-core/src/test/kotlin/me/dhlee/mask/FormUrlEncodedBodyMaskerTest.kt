package me.dhlee.mask

import me.dhlee.properties.HttpLoggingProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class FormUrlEncodedBodyMaskerTest {

    private val keyToMask = "email"

    private val masker = FormUrlEncodedBodyMasker(
        HttpLoggingProperties(maskBody = setOf(keyToMask))
    )

    @Test
    fun `마스킹 대상 키의 값을 대소문자와 관계없이 마스킹할 수 있다`() {
        val input = "name=userA=userB&${keyToMask.uppercase()}=testemail@email.com"
        val expected = "name=userA=userB&${keyToMask.uppercase()}=****"

        val masker = masker.mask(input)

        assertEquals(expected, masker)
    }

    @Test
    fun `URL 디코딩된 키 기준으로 마스킹할 수 있다`() {
        val encodedKeyToMask = keyToMask.toByteArray(Charsets.US_ASCII).joinToString("") {
            "%${String.format("%02X", it)}"
        }
        val input = "$encodedKeyToMask=test@test.com"
        val expected = "$keyToMask=****"

        val result = masker.mask(input)

        assertEquals(expected, result)
    }

    @Test
    fun `마스킹 대상 키와 동일한 문자열을 가진 값은 마스킹되지 않는다`() {
        val input = "username=$keyToMask"
        val expected = "username=$keyToMask"

        val result = masker.mask(input)

        assertEquals(expected, result)
    }

    @Test
    fun `마스킹 대상 키의 값이 비어있는 경우에도 마스킹한 값을 반환한다`() {
        val input = "$keyToMask="
        val expected = "$keyToMask=****"

        val result = masker.mask(input)

        assertEquals(expected, result)
    }

    @Test
    fun `null 또는 빈 문자열인 경우 빈 문자열을 반환한다`() {
        assertTrue(masker.mask("") == "")
        assertTrue(masker.mask("  ") == "")
        assertTrue(masker.mask(null) == "")
    }
}