package com.streaming.spring_boot.playback.repository

import com.streaming.spring_boot.playback.model.NodeStatus
import com.streaming.spring_boot.playback.model.StreamingNode
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant

interface StreamingNodeRepository : MongoRepository<StreamingNode, String> {

    fun findByStatus(status: NodeStatus): List<StreamingNode>

    fun findByLastHeartbeatBefore(time: Instant): List<StreamingNode>
}