package com.streaming.spring_boot.playback.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "streaming_nodes")
data class StreamingNode(
    @Id
    val id: String? = null,
    val host: String,
    val port: Int = 8080,
    val status: NodeStatus = NodeStatus.OFFLINE,
    val lastHeartbeat: Instant = Instant.now(),
    val activeStreams: Int = 0,
    val capacity: Int = 100
)

enum class NodeStatus {
    ONLINE,
    OFFLINE
}