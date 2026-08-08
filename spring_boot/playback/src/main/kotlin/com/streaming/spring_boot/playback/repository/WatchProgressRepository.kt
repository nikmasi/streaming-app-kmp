package com.streaming.spring_boot.playback.repository

import com.streaming.spring_boot.playback.model.WatchProgress
import org.springframework.data.mongodb.repository.MongoRepository

interface WatchProgressRepository : MongoRepository<WatchProgress, String> {

    fun findByUserIdAndMovieId(userId: String, movieId: String): WatchProgress?

    fun findByUserIdAndCompletedFalseOrderByLastWatchedAtDesc(userId: String): List<WatchProgress>

    fun findByUserIdOrderByLastWatchedAtDesc(userId: String): List<WatchProgress>
}