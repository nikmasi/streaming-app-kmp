package com.streaming.spring_boot.catalog.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("movies")
data class Movie(
    @Id val id: ObjectId? = null,
    val title: String,
    val description: String,
    val genres: List<String>,
    val duration: Int,
    val releaseYear: Int,
    val thumbnailUrl: String,
    val videoUrl: String,
)