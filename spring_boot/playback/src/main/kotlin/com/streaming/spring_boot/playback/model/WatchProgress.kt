package com.streaming.spring_boot.playback.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "watch_progress")
data class WatchProgress(
    @Id
    val id: String? = null,

    val userId: String,
    val movieId: String,

    var positionSeconds: Int = 0,
    var durationSeconds: Int = 0,

    var completed: Boolean = false,

    var lastWatchedAt: Instant = Instant.now()
)
