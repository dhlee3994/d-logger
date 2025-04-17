package me.dhlee.filter

interface PathFilter {

    fun shouldLog(path: String): Boolean
}
