package com.streaming.spring_boot.user.controllers

//import com.streaming.spring_boot.security.UserService
//import com.streaming.spring_boot.user.model.Search
//import jakarta.validation.Valid
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//@RequestMapping("/user")
//class UserController(
//    private val userService: UserService
//) {
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
//}

import com.streaming.spring_boot.user.UserService
import com.streaming.spring_boot.user.model.ChangePasswordRequest
import com.streaming.spring_boot.user.model.ProfileRequest
import com.streaming.spring_boot.user.model.ProfileResponse
import jakarta.annotation.PostConstruct
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping("/admin/users")
    fun getAllUsers(): ResponseEntity<List<ProfileResponse>> {
        return ResponseEntity.ok(service.getAllUsers())
    }

}