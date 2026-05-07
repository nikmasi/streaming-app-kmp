package com.streaming.spring_boot.favourites.service

import com.streaming.spring_boot.favourites.model.Favourite
import com.streaming.spring_boot.favourites.repository.FavouriteRepository
import org.springframework.stereotype.Service


@Service
class FavouriteService(
    private val favouriteRepository: FavouriteRepository,
) {
    fun findByUserId(userId: String): Favourite?{
        return favouriteRepository.findByUserId(userId)
    }
}