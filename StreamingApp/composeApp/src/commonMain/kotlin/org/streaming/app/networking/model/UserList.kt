package org.streaming.app.networking.model

import kotlinx.serialization.Serializable

@Serializable
data class UserList(
    val id: Long,
    var userId: Long,
    var movieId: Long,
    var type: ListType,
    var createdAt: String
)

enum class ListType{
    MY_LIST,
    FAVORITE
}