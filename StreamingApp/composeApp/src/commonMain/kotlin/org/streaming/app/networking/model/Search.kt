package org.streaming.app.networking.model

import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val idSearch: Long,
    val idUser: Long,
    val query: String,
    var count: Int,
    var createdAt: String,
    var updatedAt: String
)