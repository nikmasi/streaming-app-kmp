package com.streaming.spring_boot.favourites.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import com.streaming.spring_boot.favourites.model.MovieResponse

@FeignClient(name = "catalog")
interface CatalogClient {
    @GetMapping("/api/v1/catalog/{movieId}")
    fun getMovie(@PathVariable movieId: String?): MovieResponse?
}