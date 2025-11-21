package com.xanroid.toddlergames

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform