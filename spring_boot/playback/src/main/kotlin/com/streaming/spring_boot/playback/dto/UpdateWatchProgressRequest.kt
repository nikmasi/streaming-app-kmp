package com.streaming.spring_boot.playback.dto

data class UpdateWatchProgressRequest(
    val movieId: String,
    val positionSeconds: Int,
    val durationSeconds: Int
)