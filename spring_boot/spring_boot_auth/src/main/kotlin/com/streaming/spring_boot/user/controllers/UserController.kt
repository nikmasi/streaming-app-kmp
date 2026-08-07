package com.streaming.spring_boot.user.controllers

import com.streaming.spring_boot.user.UserService
import com.streaming.spring_boot.user.model.ChangePasswordRequest
import com.streaming.spring_boot.user.model.ProfileRequest
import com.streaming.spring_boot.user.model.ProfileResponse
import jakarta.annotation.PostConstruct
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val service: UserService
) {

    @PostConstruct
    fun init() {
        println("USER CONTROLLER LOADED")
    }

    @PatchMapping
    fun changePassword(
        @RequestBody request: ChangePasswordRequest,
        connectedUser: Principal
    ): ResponseEntity<Unit> {
        service.changePassword(request, connectedUser)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/profile")
    fun profile(
        @RequestBody request: ProfileRequest
    ): ResponseEntity<ProfileResponse> {
        return ResponseEntity.ok(service.profile(request))
    }

    @PostMapping("/edit-profile")
    fun editProfile(
        @RequestBody request: ProfileResponse
    ): ResponseEntity<ProfileResponse> {
        return ResponseEntity.ok(service.editProfile(request))
    }
}