package com.streaming.spring_boot.playback.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class StreamingNodeHeartbeat(
    private val nodeService: StreamingNodeService,

    @Value("\${streaming.node.id}")
    private val nodeId: String
) {

    @Scheduled(fixedRate = 5000)
    fun sendHeartbeat() {
        try {
            nodeService.heartbeat(nodeId)

            println("Heartbeat sent: $nodeId")
        } catch (e: Exception) {
            println("Heartbeat failed for $nodeId: ${e.message}")
        }
    }
}