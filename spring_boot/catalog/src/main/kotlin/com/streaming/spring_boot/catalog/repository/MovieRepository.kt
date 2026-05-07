package com.streaming.spring_boot.catalog.repository

import com.streaming.spring_boot.catalog.model.Movie
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MovieRepository : MongoRepository<Movie, ObjectId> {
    fun findByTitleContainingIgnoreCase(title: String): List<Movie>?

    //findBy field condition
    fun findTop5ByOrderByReleaseYearDesc(): List<Movie>?

    fun findTop5ByGenresOrderByReleaseYearDesc(genre: String): List<Movie>?

    fun findTop10ByGenres(genre: String): List<Movie>
}