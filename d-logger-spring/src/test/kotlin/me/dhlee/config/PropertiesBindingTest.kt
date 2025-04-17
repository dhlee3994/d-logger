package me.dhlee.config

import me.dhlee.filter.PathFilter
import me.dhlee.properties.SpringHttpLoggingProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ImportAutoConfiguration(HttpLoggingAutoConfiguration::class)
@SpringBootTest(classes = [PropertiesBindingTest::class])
class PropertiesBindingTest {

    @Autowired
    lateinit var props: SpringHttpLoggingProperties

    @Autowired
    lateinit var pathFilter: PathFilter

    @Test
    fun `application yml 설정이 SpringHttpLoggingProperties에 바인딩된다`() {
        assertEquals(setOf("/api/**"), props.includePaths)
        assertEquals(setOf("/health"), props.excludePaths)
        assertEquals(setOf("Authorization"), props.maskHeaders)
        assertEquals(setOf("password"), props.maskBody)
    }

    @Test
    fun `PathFilter에 설정이 실제로 반영된다`() {
        assertTrue(pathFilter.shouldLog("/api/user"))
        assertFalse(pathFilter.shouldLog("/health"))
    }
}