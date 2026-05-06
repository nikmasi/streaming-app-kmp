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
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val service: UserService
) {

    @PatchMapping
    fun changePassword(
        @RequestBody request: ChangePasswordRequest,
        connectedUser: Principal
    ): ResponseEntity<Unit> {
        service.changePassword(request, connectedUser)
        return ResponseEntity.ok().build()
    }
}