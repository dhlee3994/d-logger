package me.dhlee.config

import me.dhlee.filter.PathFilter
import me.dhlee.logger.HttpLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.web.servlet.HandlerInterceptor
import kotlin.test.Test
import kotlin.test.assertNotNull

@ImportAutoConfiguration(HttpLoggingAutoConfiguration::class)
@SpringBootTest(classes = [HttpLoggingAutoConfigurationTest::class])
class HttpLoggingAutoConfigurationTest {

    @Autowired
    lateinit var httpLogger: HttpLogger

    @Autowired
    lateinit var pathFilter: PathFilter

    @Autowired
    lateinit var httpLoggingFilter: FilterRegistrationBean<*>

    @Autowired
    lateinit var interceptor: HandlerInterceptor

    @Test
    fun `auto configuration이 bean들을 정상 등록한다`() {
        assertNotNull(httpLogger)
        assertNotNull(pathFilter)
        assertNotNull(httpLoggingFilter)
        assertNotNull(interceptor)
    }
}