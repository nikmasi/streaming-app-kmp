package com.streaming.spring_boot.favourites.repository

import com.streaming.spring_boot.favourites.model.Favourite
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface FavouriteRepository : MongoRepository<Favourite, ObjectId> {
    fun findByUserId(userId: String): Favourite?
}