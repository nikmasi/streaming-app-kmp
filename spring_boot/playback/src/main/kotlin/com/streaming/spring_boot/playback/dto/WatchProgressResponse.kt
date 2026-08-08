package com.streaming.spring_boot.playback.dto

import java.time.Instant

data class WatchProgressResponse(
    val movieId: String,
    val positionSeconds: Int,
    val durationSeconds: Int,
    val completed: Boolean,
    val lastWatchedAt: Instant
)