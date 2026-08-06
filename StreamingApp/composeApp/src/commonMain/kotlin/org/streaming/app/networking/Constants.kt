package org.streaming.app.networking

object Constants {
    const val BASE_URL = "http://10.0.2.2:8222"

    fun imageUrl(path: String): String {
        return "$BASE_URL/api/v1/catalog/$path"
    }
}
