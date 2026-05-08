package com.streaming.spring_boot.favourites.service

import com.streaming.spring_boot.favourites.client.CatalogClient
import com.streaming.spring_boot.favourites.model.Favourite
import com.streaming.spring_boot.favourites.repository.FavouriteRepository
import org.springframework.stereotype.Service


@Service
class FavouriteService(
    private val favouriteRepository: FavouriteRepository,
    private val catalogClient: CatalogClient
) {
    fun findByUserId(userId: String): Favourite?{
        return favouriteRepository.findByUserId(userId)
    }

    fun addFavourite(movieId: String?) {
        val movie = catalogClient.getMovie(movieId)
        // ako film postoji -> save
    }
}