package com.streaming.spring_boot.playback.controller

import com.streaming.spring_boot.playback.dto.UpdateWatchProgressRequest
import com.streaming.spring_boot.playback.dto.WatchProgressResponse
import com.streaming.spring_boot.playback.service.PlaybackService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@RequestMapping("/api/v1/playback")
@CrossOrigin(origins = ["http://localhost:4200"])
public class PlaybackController(
    private val playbackService: PlaybackService
){
    data class PlaybackResponse(
        val streamUrl: String
    )

    @PostMapping("/video")
    fun uploadVideo(
        @RequestParam movieId: String,
        @RequestParam video: MultipartFile
    ): PlaybackResponse {
        val uploadDir = Paths.get("storage/uploads")
        Files.createDirectories(uploadDir)

        val videoPath = uploadDir.resolve(video.originalFilename!!)

        video.transferTo(videoPath)

        val hlsPath = Paths.get("storage/hls/$movieId")

        playbackService.convertToHls(videoPath, hlsPath)

        return PlaybackResponse(
            streamUrl = "/hls/$movieId/master.m3u8"
        )
    }

    @GetMapping("/video")
    fun getVideo(
        @RequestParam movieId: String
    ): PlaybackResponse {

        return PlaybackResponse(
            streamUrl = "/hls/$movieId/master.m3u8"
        )
    }


    // watch

    @PutMapping("/progress")
    fun updateProgress(
        @RequestHeader("X-User-Email") email: String,
        @RequestBody request: UpdateWatchProgressRequest
    ): WatchProgressResponse {
        return playbackService.updateProgress(email, request)
    }

    @GetMapping("/progress/{movieId}")
    fun getProgress(
        @RequestHeader("X-User-Email") email: String,
        @PathVariable movieId: String
    ): WatchProgressResponse? {
        return playbackService.getProgress(email, movieId)
    }

    @GetMapping("/continue-watching")
    fun continueWatching(
        @RequestHeader("X-User-Email") email: String
    ): List<WatchProgressResponse> {
        return playbackService.getContinueWatching(email)
    }

    @GetMapping("/history")
    fun history(
        @RequestHeader("X-User-Email") email: String
    ): List<WatchProgressResponse> {
        return playbackService.getHistory(email)
    }
}