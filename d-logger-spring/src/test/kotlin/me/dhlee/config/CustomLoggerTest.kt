package me.dhlee.config

import me.dhlee.logger.HttpLogger
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import kotlin.test.assertTrue

@ImportAutoConfiguration(HttpLoggingAutoConfiguration::class)
@SpringBootTest(classes = [CustomLoggerTest::class, TestConfig::class])
class CustomLoggerTest {

    @Autowired
    lateinit var httpLogger: HttpLogger

    @Test
    fun `사용자 정의 HttpLogger 빈이 우선 적용된다`() {
        assertTrue(Mockito.mockingDetails(httpLogger).isMock)
    }
}