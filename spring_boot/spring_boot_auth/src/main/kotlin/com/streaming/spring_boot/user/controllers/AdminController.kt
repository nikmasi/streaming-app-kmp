package com.streaming.spring_boot.user.controllers

import com.streaming.spring_boot.auth.RegisterRequest
import com.streaming.spring_boot.user.UserService
import com.streaming.spring_boot.user.model.ProfileRequest
import com.streaming.spring_boot.user.model.ProfileResponse
import com.streaming.spring_boot.user.model.Role
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class AdminController(
    private val service: UserService
) {
    data class CreateUserResponse(
        val id: Long? = null,
        val email: String,
        val role: Role
    )

    data class DeleteUserRequest(
        val email: String
    )

    data class InfoResponse(
        val movieNumber: Int,
        val tvShowNumber: Int,
        val userNumber: Int
    )

    @GetMapping("/admin/users")
    fun getAllUsers(): ResponseEntity<List<ProfileResponse>> {
        return ResponseEntity.ok(service.getAllUsers())
    }

    @PostMapping("/admin/add-user")
    fun addUser(
        @RequestBody request: RegisterRequest
    ): ResponseEntity<CreateUserResponse> {
        return ResponseEntity.ok(service.createUser(request))
    }

    @PostMapping("/admin/edit-user")
    fun editUser(
        @RequestBody request: ProfileResponse
    ): ResponseEntity<ProfileResponse> {
        return ResponseEntity.ok(service.editProfile(request))
    }

    @DeleteMapping("/admin/delete-user")
    fun deleteUser(
        @RequestBody request: DeleteUserRequest
    ): ResponseEntity<String> {
        service.deleteUser(request.email)

        return ResponseEntity.ok("User deleted")
    }

    @GetMapping("/admin/info")
    fun getInfo(): ResponseEntity<InfoResponse> {
        return ResponseEntity.ok(service.getInfo())
    }
}