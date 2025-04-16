package me.dhlee.context

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.slf4j.MDC

private const val TRACE_ID_KEY = "d-logger-trace-id"

class TraceIdProviderTest {

    @AfterEach
    fun tearDown() {
        MDC.clear()
    }

    @Test
    fun `traceId가 저장되어 있으면 traceId를 반환한다`() {
        val traceId = "test-trace-id"
        TraceIdProvider.set(traceId)

        val result = TraceIdProvider.getOrCreate()

        assertEquals(traceId, result)
    }

    @Test
    fun `traceId가 없으면 새로 생성해서 반환한다`() {
        val result = TraceIdProvider.getOrCreate()

        assertNotNull(result)
        assertEquals(result, MDC.get(TRACE_ID_KEY))
    }

    @Test
    fun `clear 하면 traceId가 제거된다`() {
        TraceIdProvider.set("test-trace-id")
        TraceIdProvider.clear()

        val result = MDC.get(TRACE_ID_KEY)
        assertEquals(null, result)
    }
}