package com.streaming.spring_boot.playback.dto

data class MovieResponse(
    val id: String?,
    val title: String?,
    val description: String,
    val genres: List<String>,
    val duration: Int,
    val releaseYear: Int,
    val thumbnailUrl: String,
    val videoUrl: String
)