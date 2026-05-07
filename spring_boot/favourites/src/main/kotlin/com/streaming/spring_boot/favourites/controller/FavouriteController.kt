package com.streaming.spring_boot.favourites.controller

import com.streaming.spring_boot.favourites.model.Favourite
import com.streaming.spring_boot.favourites.service.FavouriteService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController(
    private val favouriteService: FavouriteService
){
    data class FavouriteRequest(val userId: String)

    @PostMapping
    fun findByUserId(
        @RequestBody request: FavouriteRequest
    ): Favourite?{
        return favouriteService.findByUserId(request.userId)
    }
}