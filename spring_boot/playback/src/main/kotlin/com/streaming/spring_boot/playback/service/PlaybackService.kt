package com.streaming.spring_boot.playback.service

import com.streaming.spring_boot.playback.repository.PlaybackRepository
import org.springframework.stereotype.Service


@Service
class PlaybackService(
    private val playbackRepository: PlaybackRepository
) {

}