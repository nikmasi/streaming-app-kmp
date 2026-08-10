package com.streaming.spring_boot.favourites.controller

import com.streaming.spring_boot.favourites.model.ContentType
import com.streaming.spring_boot.favourites.model.PreferenceStatus
import com.streaming.spring_boot.favourites.model.UserContentPreference
import com.streaming.spring_boot.favourites.service.FavouriteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/favourites")
class FavouriteController(
    private val favouriteService: FavouriteService
) {

    @GetMapping("/{userId}")
    fun getAll(
        @PathVariable userId: String
    ): List<UserContentPreference> {
        return favouriteService.getAllByUserId(userId)
    }

    @GetMapping("/{userId}/status/{status}")
    fun getByStatus(
        @PathVariable userId: String,
        @PathVariable status: PreferenceStatus
    ): List<UserContentPreference> {
        return favouriteService.getByStatus(
            userId,
            status
        )
    }

    @GetMapping("/{userId}/{contentType}/{contentId}")
    fun getPreference(
        @PathVariable userId: String,
        @PathVariable contentType: ContentType,
        @PathVariable contentId: String
    ): ResponseEntity<UserContentPreference> {

        val preference = favouriteService.getPreference(
            userId, contentId, contentType
        )

        return if (preference != null) {
            ResponseEntity.ok(preference)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{userId}")
    fun setPreference(
        @PathVariable userId: String,
        @RequestBody request: FavouriteService.PreferenceRequest
    ): UserContentPreference {
        return favouriteService.setPreference(userId, request)
    }

    @DeleteMapping("/{userId}/{contentType}/{contentId}")
    fun removePreference(
        @PathVariable userId: String,
        @PathVariable contentType: ContentType,
        @PathVariable contentId: String
    ): ResponseEntity<Void> {
        favouriteService.removePreference(userId, contentId, contentType)
        return ResponseEntity.noContent().build()
    }
}