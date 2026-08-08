package com.streaming.spring_boot.playback.service

import com.streaming.spring_boot.playback.dto.UpdateWatchProgressRequest
import com.streaming.spring_boot.playback.dto.WatchProgressResponse
import com.streaming.spring_boot.playback.model.WatchProgress
import com.streaming.spring_boot.playback.repository.WatchProgressRepository
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant

@Service
class PlaybackService(
    private val repository: WatchProgressRepository
) {
    fun convertToHls(input: Path, outputDir: Path) {
        Files.createDirectories(outputDir)

        val process = ProcessBuilder(
            "ffmpeg",
            "-i", input.toString(),
            "-codec:", "copy",
            "-start_number", "0",
            "-hls_time", "6",
            "-hls_list_size", "0",
            "-f", "hls",
            outputDir.resolve("master.m3u8").toString()
        )
            .redirectErrorStream(true)
            .start()

        process.inputStream.bufferedReader().use {
            it.lines().forEach(::println)
        }

        val exitCode = process.waitFor()

        if (exitCode != 0) {
            throw RuntimeException("FFmpeg failed with exit code $exitCode")
        }
    }

    // watch

    fun updateProgress(
        userId: String,
        request: UpdateWatchProgressRequest
    ): WatchProgressResponse {

        val progress = repository.findByUserIdAndMovieId(userId, request.movieId)
            ?: WatchProgress(userId = userId, movieId = request.movieId)

        progress.positionSeconds = request.positionSeconds
        progress.durationSeconds = request.durationSeconds

        progress.completed = request.durationSeconds > 0 &&
                    request.positionSeconds >= request.durationSeconds * 0.95

        progress.lastWatchedAt = Instant.now()
        val saved = repository.save(progress)

        return saved.toResponse()
    }

    fun getProgress(userId: String, movieId: String): WatchProgressResponse? {
        return repository.findByUserIdAndMovieId(userId, movieId)?.toResponse()
    }

    fun getContinueWatching(userId: String): List<WatchProgressResponse> {
        return repository
            .findByUserIdAndCompletedFalseOrderByLastWatchedAtDesc(userId)
            .filter { it.positionSeconds > 0 }
            .map { it.toResponse() }
    }

    fun getHistory(userId: String): List<WatchProgressResponse> {
        return repository.findByUserIdOrderByLastWatchedAtDesc(userId).map { it.toResponse() }
    }

    private fun WatchProgress.toResponse() =
        WatchProgressResponse(
            movieId = movieId,
            positionSeconds = positionSeconds,
            durationSeconds = durationSeconds,
            completed = completed,
            lastWatchedAt = lastWatchedAt
        )
}