package com.streaming.spring_boot.favourites.model

import org.bson.types.ObjectId

data class MovieResponse(
    val id: ObjectId? = null,
    val title: String,
    val description: String,
    val genres: List<String>,
    val duration: Int,
    val releaseYear: Int,
    val thumbnailUrl: String,
    val videoUrl: String,
)