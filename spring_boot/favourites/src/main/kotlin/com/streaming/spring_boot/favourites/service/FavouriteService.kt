package com.streaming.spring_boot.favourites.service

import com.streaming.spring_boot.favourites.model.ContentType
import com.streaming.spring_boot.favourites.model.PreferenceStatus
import com.streaming.spring_boot.favourites.model.UserContentPreference
import com.streaming.spring_boot.favourites.repository.FavouriteRepository
import org.springframework.stereotype.Service

@Service
class FavouriteService(
    private val favouriteRepository: FavouriteRepository
) {

    data class PreferenceRequest(
        val contentId: String,
        val contentType: ContentType,
        val status: PreferenceStatus
    )

    fun getAllByUserId(userId: String): List<UserContentPreference> {
        return favouriteRepository.findAllByUserId(userId)
    }

    fun getByStatus(userId: String, status: PreferenceStatus): List<UserContentPreference> {
        return favouriteRepository.findAllByUserIdAndStatus(userId, status)
    }

    fun getPreference(
        userId: String, contentId: String, contentType: ContentType
    ): UserContentPreference? {
        return favouriteRepository
            .findByUserIdAndContentIdAndContentType(userId, contentId, contentType)
    }

    fun setPreference(userId: String, request: PreferenceRequest): UserContentPreference {

        val existing = favouriteRepository
            .findByUserIdAndContentIdAndContentType(
                userId, request.contentId, request.contentType
            )

        val preference = existing?.copy(
            status = request.status
        ) ?: UserContentPreference(
            userId = userId,
            contentId = request.contentId,
            contentType = request.contentType,
            status = request.status
        )

        return favouriteRepository.save(preference)
    }

    fun removePreference(userId: String, contentId: String, contentType: ContentType) {
        favouriteRepository.deleteByUserIdAndContentIdAndContentType(
            userId, contentId, contentType
        )
    }
}