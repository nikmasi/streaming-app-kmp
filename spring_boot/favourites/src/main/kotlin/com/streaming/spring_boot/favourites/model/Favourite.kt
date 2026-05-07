package com.streaming.spring_boot.favourites.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("favourites")
data class Favourite(
    @Id val id: ObjectId? = null,
    val userId: String,
    val movieIds: List<String> = emptyList()
)