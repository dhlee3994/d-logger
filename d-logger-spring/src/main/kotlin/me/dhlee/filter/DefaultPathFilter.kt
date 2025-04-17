package me.dhlee.filter

import org.springframework.util.AntPathMatcher
import org.springframework.util.PathMatcher

class DefaultPathFilter (
    private val include: Set<String>,
    private val exclude: Set<String>,
    private val matcher: PathMatcher = AntPathMatcher(),
) : PathFilter {

    override fun shouldLog(path: String): Boolean {
        return when {
            exclude.any { matcher.match(it, path) } -> false
            include.none() -> true
            else -> include.any { matcher.match(it, path) }
        }
    }
}