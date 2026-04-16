package com.streaming.spring_boot.movie.service

import com.streaming.spring_boot.movie.model.Movie
import com.streaming.spring_boot.movie.repository.MovieRepository
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieRepository: MovieRepository
) {

    fun search(title: String): List<Movie>?{
        val movies = movieRepository.findByTitleContainingIgnoreCase(title)
        return movies
    }

    fun yearTop5(): List<Movie>? {
        val movies = movieRepository.findTop5ByOrderByReleaseYearDesc()
        return movies
    }

    fun top5ByGenreOrderByYear(genre: String): List<Movie>? {
        val movies = movieRepository.findTop5ByGenreOrderByReleaseYearDesc(genre)
        return movies
    }
}