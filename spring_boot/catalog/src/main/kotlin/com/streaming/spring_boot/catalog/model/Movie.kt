package com.streaming.spring_boot.catalog.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Movie(
    @Id
    @GeneratedValue
    val id: Long = 0,

    val title: String ="",
    val description: String ="",

    val genre: String="",
    val duration: Int=0, // u min

    @Column(name = "\"releaseyear\"", nullable = false)
    val releaseYear: Int = 0,

    @Column(name = "\"thumbnailurl\"", nullable = false)
    val thumbnailUrl: String ="",
    @Column(name = "\"videourl\"", nullable = false)
    val videoUrl: String=""
)