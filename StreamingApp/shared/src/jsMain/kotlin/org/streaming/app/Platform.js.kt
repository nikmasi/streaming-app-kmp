package org.streaming.app

class JsPlatform: Platform {
    override val name: String = "Web"
}

actual fun getPlatform(): Platform = JsPlatform()