package me.dhlee.filter

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.dhlee.common.HttpLoggingConstants.DLOGGER_LOG_MARKER_KEY
import me.dhlee.common.HttpLoggingConstants.DLOGGER_LOG_MARKER_VALUE
import org.mockito.Mockito.*
import kotlin.test.Test

class HttpLoggingInterceptorTest {

    private val interceptor = HttpLoggingInterceptor()

    @Test
    fun `예외가 발생하면 로그 마커를 설정한다`() {
        val request = mock<HttpServletRequest>()
        val response = mock<HttpServletResponse>()
        val handler = Any()
        val exception = Exception("Test Exception")

        interceptor.afterCompletion(request, response, handler, exception)

        verify(request).setAttribute(DLOGGER_LOG_MARKER_KEY, DLOGGER_LOG_MARKER_VALUE)
    }

    @Test
    fun `예외가 발생하지 않으면 로그 마커를 설정하지 않는다`() {
        val request = mock<HttpServletRequest>()
        val response = mock<HttpServletResponse>()
        val handler = Any()

        interceptor.afterCompletion(request, response, handler, null)

        verify(request, never()).setAttribute(any(), any())
    }
}