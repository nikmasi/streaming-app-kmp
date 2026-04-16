package org.streaming.app.networking.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Long,
    val title: String,
    val description: String,
    val genre: String,
    val duration: Int, // u min
    val releaseYear: Int,
    val thumbnailUrl: String,
    val videoUrl: String
)