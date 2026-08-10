package com.streaming.spring_boot.favourites.repository

import com.streaming.spring_boot.favourites.model.ContentType
import com.streaming.spring_boot.favourites.model.PreferenceStatus
import com.streaming.spring_boot.favourites.model.UserContentPreference
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface FavouriteRepository : MongoRepository<UserContentPreference, ObjectId> {

    fun findByUserIdAndContentIdAndContentType(
        userId: String, contentId: String, contentType: ContentType
    ): UserContentPreference?

    fun findAllByUserId(userId: String): List<UserContentPreference>

    fun findAllByUserIdAndStatus(userId: String, status: PreferenceStatus):
            List<UserContentPreference>

    fun deleteByUserIdAndContentIdAndContentType(
        userId: String, contentId: String, contentType: ContentType
    )
}