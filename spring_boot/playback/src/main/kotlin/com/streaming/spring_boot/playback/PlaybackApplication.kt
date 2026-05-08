package com.streaming.spring_boot.playback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlaybackApplication

fun main(args: Array<String>) {
	runApplication<PlaybackApplication>(*args)
}
