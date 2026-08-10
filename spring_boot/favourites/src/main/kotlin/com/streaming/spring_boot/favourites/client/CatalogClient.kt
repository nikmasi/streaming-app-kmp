package com.streaming.spring_boot.favourites.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import com.streaming.spring_boot.favourites.model.MovieResponse
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "catalog")
interface CatalogClient {
    @GetMapping("/api/v1/catalog/movie/{id}")
    fun getMovieById(
        @PathVariable("id") id: String
    ): MovieResponse?

    @GetMapping("/api/v1/catalog/movies/by-ids")
    fun getMoviesByIds(
        @RequestParam("ids") ids: List<String>
    ): List<MovieResponse>
}