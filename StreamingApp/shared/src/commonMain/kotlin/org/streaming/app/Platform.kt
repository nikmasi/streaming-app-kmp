package org.streaming.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform