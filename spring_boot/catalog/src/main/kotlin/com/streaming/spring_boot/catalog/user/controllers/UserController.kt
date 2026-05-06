package com.streaming.spring_boot.catalog.user.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    //private val userService: UserService
) {
//    data class ProfileImageRequest(
//        val email: String,
//        val profileImage: String?
//    )
//
//    data class SearchContentRequest(
//        val email: String
//    )
//
//    @PostMapping("/update-profile-image")
//    fun updateProfileImage(
//        @Valid @RequestBody body: ProfileImageRequest
//    ): Boolean {
//        return userService.updateProfileImage(body.email, body.profileImage)
//    }
//
//    @PostMapping("/search-history-top")
//    fun searchHistoryTop(
//        @Valid @RequestBody body: SearchContentRequest
//    ): List<Search>{
//        return userService.findTop10SearchContent(body.email)
//    }
}