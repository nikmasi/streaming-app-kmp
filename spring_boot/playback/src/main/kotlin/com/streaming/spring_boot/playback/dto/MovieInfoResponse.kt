package com.streaming.spring_boot.playback.dto

data class MovieInfoResponse(
    val id: String,
    val title: String,
    val thumbnailUrl: String?
)