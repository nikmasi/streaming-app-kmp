package com.streaming.spring_boot.favourites.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

// userId + contentId + contentType is unique

@Document("user_content_preferences")
@CompoundIndex(
    name = "user_content_unique",
    def = "{'userId': 1, 'contentId': 1, 'contentType': 1}",
    unique = true
)
data class UserContentPreference(
    @Id
    val id: ObjectId? = null,

    @Indexed
    val userId: String,

    @Indexed
    val contentId: String,

    val contentType: ContentType,

    val status: PreferenceStatus
)

enum class PreferenceStatus {
    LIKED,
    DISLIKED,
    WATCH_LATER
}

enum class ContentType {
    MOVIE,
    TV_SHOW
}