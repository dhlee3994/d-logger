package me.dhlee.filter

import org.springframework.util.AntPathMatcher
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DefaultPathFilterTest {

    private val matcher = AntPathMatcher()

    @Test
    fun `exclude 경로에 매치되면 false를 반한다`() {
        val excludePath = "/api/test"

        val filter = DefaultPathFilter(
            include = setOf("/api/**"),
            exclude = setOf(excludePath),
            matcher = matcher,
        )

        val result = filter.shouldLog(excludePath)

        assertFalse(result)
    }

    @Test
    fun `include 경로에 매치되면 true를 반환한다`() {
        val includePath = "/api/test"

        val filter = DefaultPathFilter(
            include = setOf("/api/**"),
            exclude = emptySet(),
            matcher = matcher,
        )

        val result = filter.shouldLog(includePath)

        assertTrue(result)
    }

    @Test
    fun `exclude, include 경로에 모두 매치되면 exclude가 우선 적용되어 false를 반환한다`() {
        val path = "/api/test"

        val filter = DefaultPathFilter(
            include = setOf(path),
            exclude = setOf(path),
            matcher = matcher,
        )

        val result = filter.shouldLog(path)

        assertFalse(result)
    }

    @Test
    fun `exclude, include 모두 비어있으면 true를 반환한다`() {
        val filter = DefaultPathFilter(
            include = emptySet(),
            exclude = emptySet(),
            matcher = matcher,
        )

        val result = filter.shouldLog("/api/test")

        assertTrue(result)
    }
}