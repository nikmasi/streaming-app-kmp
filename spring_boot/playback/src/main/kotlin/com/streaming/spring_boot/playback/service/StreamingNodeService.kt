package com.streaming.spring_boot.playback.service

import com.streaming.spring_boot.playback.model.NodeStatus
import com.streaming.spring_boot.playback.model.StreamingNode
import com.streaming.spring_boot.playback.repository.StreamingNodeRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class StreamingNodeService(
    private val repository: StreamingNodeRepository
) {

    fun register(node: StreamingNode): StreamingNode {
        return repository.save(node)
    }

    fun heartbeat(nodeId: String): StreamingNode {
        val node = repository.findById(nodeId)
            .orElseThrow {
                IllegalArgumentException("Streaming node not found: $nodeId")
            }

        val updatedNode = node.copy(
            status = NodeStatus.ONLINE,
            lastHeartbeat = Instant.now()
        )

        return repository.save(updatedNode)
    }

    fun getAll(): List<StreamingNode> {
        return repository.findAll()
    }
}