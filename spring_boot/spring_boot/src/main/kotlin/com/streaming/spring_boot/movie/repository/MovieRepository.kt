package com.streaming.spring_boot.movie.repository

import com.streaming.spring_boot.movie.model.ListType
import com.streaming.spring_boot.movie.model.Movie
import com.streaming.spring_boot.movie.model.UserList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MovieRepository : JpaRepository<Movie, Long> {
    fun findByTitleContainingIgnoreCase(title: String): List<Movie>?

    //findBy field condition
    fun findTop5ByOrderByReleaseYearDesc(): List<Movie>?

    fun findTop5ByGenreOrderByReleaseYearDesc(genre: String): List<Movie>?
}