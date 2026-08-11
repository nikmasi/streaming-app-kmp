package com.streaming.spring_boot.playback.service

import com.streaming.spring_boot.playback.model.NodeStatus
import com.streaming.spring_boot.playback.model.StreamingNode
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class StreamingNodeRegistration(
    private val nodeService: StreamingNodeService,

    @Value("\${streaming.node.id}")
    private val nodeId: String,

    @Value("\${streaming.node.host}")
    private val host: String,

    @Value("\${streaming.node.port}")
    private val port: Int,

    @Value("\${streaming.node.capacity}")
    private val capacity: Int
) {

    @EventListener(ApplicationReadyEvent::class)
    fun register() {

        val node = StreamingNode(
            id = nodeId,
            host = host,
            port = port,
            status = NodeStatus.ONLINE,
            activeStreams = 0,
            capacity = capacity
        )

        nodeService.register(node)

        println("Streaming node registered: $nodeId")
    }
}