package com.streaming.spring_boot.movie.repository

import com.streaming.spring_boot.movie.model.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository : JpaRepository<Movie, Long> {
    fun findByTitleContainingIgnoreCase(title: String): List<Movie>?

    //findBy field condition
    fun findTop5ByOrderByReleaseYearDesc(): List<Movie>?

    fun findTop5ByGenreOrderByReleaseYearDesc(genre: String): List<Movie>?
}