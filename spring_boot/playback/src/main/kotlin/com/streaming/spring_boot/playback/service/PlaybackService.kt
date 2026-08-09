package com.streaming.spring_boot.playback.service

import com.streaming.spring_boot.playback.client.CatalogClient
import com.streaming.spring_boot.playback.dto.UpdateWatchProgressRequest
import com.streaming.spring_boot.playback.dto.WatchProgressResponse
import com.streaming.spring_boot.playback.model.WatchProgress
import com.streaming.spring_boot.playback.repository.WatchProgressRepository
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import org.springframework.http.HttpStatus

@Service
class PlaybackService(
    private val repository: WatchProgressRepository,
    private val catalogClient: CatalogClient
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

        val progress = repository.findByUserIdAndMovieId(
            userId,
            request.movieId
        ) ?: WatchProgress(
            userId = userId,
            movieId = request.movieId
        )

        progress.positionSeconds = request.positionSeconds
        progress.durationSeconds = request.durationSeconds

        progress.completed =
            request.durationSeconds > 0 &&
                    request.positionSeconds >= request.durationSeconds * 0.95

        progress.lastWatchedAt = Instant.now()

        val saved = repository.save(progress)

        return toResponse(saved)
    }

    fun getProgress(userId: String, movieId: String): WatchProgressResponse? {
        val progress = repository.findByUserIdAndMovieId(
            userId, movieId
        ) ?: return null

        return toResponse(progress)
    }

    fun getContinueWatching(userId: String): List<WatchProgressResponse> {
        val progressList = repository
            .findByUserIdAndCompletedFalseOrderByLastWatchedAtDesc(userId)
            .filter { it.positionSeconds > 0 }

        if (progressList.isEmpty()) {
            return emptyList()
        }

        val movieIds = progressList.map { it.movieId }
        val movies = catalogClient.getMoviesByIds(movieIds)
        val moviesById = movies.associateBy { it.id }

        return progressList.mapNotNull { progress ->
            val movie = moviesById[progress.movieId] ?: return@mapNotNull null

            WatchProgressResponse(
                movieId = progress.movieId,
                movie = movie,
                positionSeconds = progress.positionSeconds,
                durationSeconds = progress.durationSeconds,
                completed = progress.completed,
                lastWatchedAt = progress.lastWatchedAt
            )
        }
    }

    fun getHistory(userId: String): List<WatchProgressResponse> {
        val progressList = repository.findByUserIdOrderByLastWatchedAtDesc(userId)

        if (progressList.isEmpty()) {
            return emptyList()
        }

        val movieIds = progressList.map { it.movieId }
        val movies = catalogClient.getMoviesByIds(movieIds)
        val moviesById = movies.associateBy { it.id }

        return progressList.mapNotNull { progress ->
            val movie = moviesById[progress.movieId] ?: return@mapNotNull null

            WatchProgressResponse(
                movieId = progress.movieId,
                movie = movie,
                positionSeconds = progress.positionSeconds,
                durationSeconds = progress.durationSeconds,
                completed = progress.completed,
                lastWatchedAt = progress.lastWatchedAt
            )
        }
    }

    private fun toResponse(progress: WatchProgress): WatchProgressResponse {

        val movie = catalogClient.getMovieById(progress.movieId)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "Movie ${progress.movieId} not found"
            )

        return WatchProgressResponse(
            movieId = progress.movieId,
            movie = movie,
            positionSeconds = progress.positionSeconds,
            durationSeconds = progress.durationSeconds,
            completed = progress.completed,
            lastWatchedAt = progress.lastWatchedAt
        )
    }
}