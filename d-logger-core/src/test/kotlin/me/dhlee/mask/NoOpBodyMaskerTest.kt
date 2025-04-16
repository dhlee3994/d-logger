package me.dhlee.mask

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class NoOpBodyMaskerTest {

    @Test
    fun `NoOpBodyMasker는 원본 값을 반환한다`() {
        val masker = NoOpBodyMasker()
        val body = "password is 1234"

        val result = masker.mask(body)

        assertEquals(body, result)
    }
}