package me.dhlee.context

import org.slf4j.MDC
import java.util.*

object TraceIdProvider {

    private const val TRACE_ID_KEY = "d-logger-trace-id"

    fun getOrCreate(): String {
        return get() ?: UUID.randomUUID().toString().also(TraceIdProvider::set)
    }

    fun get(): String? = MDC.get(TRACE_ID_KEY)

    fun set(traceId: String) = MDC.put(TRACE_ID_KEY, traceId)

    fun clear() = MDC.remove(TRACE_ID_KEY)
}