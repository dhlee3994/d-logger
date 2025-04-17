package me.dhlee.config

import me.dhlee.logger.HttpLogger
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestConfig {
    @Bean
    fun customHttpLogger(): HttpLogger {
        return mock(HttpLogger::class.java)
    }
}