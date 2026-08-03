package com.streaming.spring_boot.catalog.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

data class PlaybackResponse(
    val streamUrl: String
)

@FeignClient(name = "playback")
interface PlaybackClient {

    @PostMapping(
        value = ["/api/v1/playback/video"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun uploadVideo(
        @RequestParam("movieId") movieId: String,
        @RequestPart("video") video: MultipartFile
    ): PlaybackResponse
}
