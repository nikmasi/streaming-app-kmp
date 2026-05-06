package com.streaming.spring_boot.catalog.repository

import com.streaming.spring_boot.catalog.model.ListType
import com.streaming.spring_boot.catalog.model.Movie
import com.streaming.spring_boot.catalog.model.UserList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserListRepository: JpaRepository<UserList, Long> {

    fun findByUserIdAndType(userId: Long, type: ListType): List<UserList>

    fun findByUserIdAndMovieIdAndType(
        userId: Long,
        movieId: Long,
        type: ListType
    ): UserList?

    @Query("""
        SELECT m FROM Movie m
        JOIN UserList uml ON uml.movieId = m.id
        WHERE uml.userId = :userId AND uml.type = :type
    """)
    fun findMyListMovies(
        userId: Long,
        type: ListType
    ): List<Movie>

    fun deleteByUserIdAndMovieIdAndType(
        userId: Long,
        movieId: Long,
        type: ListType
    )
}