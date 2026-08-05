package com.streaming.spring_boot.playback.controller

import com.streaming.spring_boot.playback.service.PlaybackService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
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

}