package org.streaming.app.networking.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: String,
    val title: String,
    val description: String,
    val genres: List<String>,
    val duration: Int, // u min
    val releaseYear: Int,
    val thumbnailUrl: String,
    val videoUrl: String
)

@Serializable
data class IdObject(
    val timestamp: Long,
    val date: String
)

@Serializable
data class HomeContent(
    val sections: Map<String, List<Movie>>
)


@Serializable
data class WatchProgress(
    val movie: Movie,
    val movieId: String,
    val positionSeconds: Double,
    val durationSeconds: Double,
    val completed: Boolean,
    val lastWatchedAt: String
)