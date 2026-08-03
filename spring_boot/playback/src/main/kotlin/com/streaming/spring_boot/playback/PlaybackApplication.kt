package com.streaming.spring_boot.playback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class PlaybackApplication

fun main(args: Array<String>) {
	runApplication<PlaybackApplication>(*args)
}
