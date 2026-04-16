package com.streaming.spring_boot.controllers

import com.streaming.spring_boot.security.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    data class ProfileImageRequest(
        val email: String,
        val profileImage: String?
    )

    @PostMapping("/update-profile-image")
    fun updateProfileImage(
        @Valid @RequestBody body: ProfileImageRequest
    ): Boolean {
        return userService.updateProfileImage(body.email, body.profileImage)
    }
}