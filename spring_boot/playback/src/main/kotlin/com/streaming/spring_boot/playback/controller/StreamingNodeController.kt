package com.streaming.spring_boot.playback.controller

import com.streaming.spring_boot.playback.model.StreamingNode
import com.streaming.spring_boot.playback.service.StreamingNodeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/internal/nodes")
class StreamingNodeController(
    private val service: StreamingNodeService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody node: StreamingNode
    ): StreamingNode {
        return service.register(node)
    }

    @PostMapping("/{nodeId}/heartbeat")
    fun heartbeat(
        @PathVariable nodeId: String
    ): StreamingNode {
        return service.heartbeat(nodeId)
    }

    @GetMapping
    fun getAll(): List<StreamingNode> {
        return service.getAll()
    }
}