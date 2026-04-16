package com.streaming.spring_boot.movie.controller

import com.streaming.spring_boot.movie.model.Movie
import com.streaming.spring_boot.movie.service.MovieService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/movies")
class MovieController(
    private val movieService: MovieService
) {
    data class MoviesTitleRequest(
        val title: String,
    )

    @GetMapping("/search")
    fun search(
        @RequestParam title: String
    ): List<Movie>? {
        return movieService.search(title)
    }

    @GetMapping("/yearTop5")
    fun yearTop5(): List<Movie>? {
        return movieService.yearTop5()
    }

    @GetMapping("/top5ByGenreOrderByYear")
    fun top5ByGenreOrderByYear(@RequestParam genre: String): List<Movie>? {
        return movieService.top5ByGenreOrderByYear(genre)
    }
}