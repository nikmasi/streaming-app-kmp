package com.streaming.spring_boot.playback.controller

import com.streaming.spring_boot.playback.service.PlaybackService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/playback")
public class PlaybackController(
    private val playbackService: PlaybackService
){

}