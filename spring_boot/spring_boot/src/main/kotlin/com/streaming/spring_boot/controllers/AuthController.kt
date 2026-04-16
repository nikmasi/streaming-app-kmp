package com.streaming.spring_boot.controllers

import com.streaming.spring_boot.security.AuthService
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    data class AuthRequest(
        @field:Email(message = "Invalid email format.")
        val email: String,
        @field:Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}\$",
            message = "Password must be at least 9 characters long and contain at least one digit, uppercase and lowercase character."
        )
        val hashedPassword: String,
        val fullName: String,
        val phone: String
    )

    data class LoginRequest(
        @field:Email(message = "Invalid email format.")
        val email: String,
        @field:Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}\$",
            message = "Password must be at least 9 characters long and contain at least one digit, uppercase and lowercase character."
        )
        val hashedPassword: String
    )

    data class RefreshRequest(
        val refreshToken: String
    )

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody body: AuthRequest
    ): AuthService.AuthResponse {
        return authService.register(body.email, body.hashedPassword, body.fullName, body.phone)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody body: LoginRequest
    ): AuthService.AuthResponse {
        return authService.login(body.email, body.hashedPassword)
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody body: RefreshRequest
    ): AuthService.TokenPair {
        return authService.refresh(body.refreshToken)
    }
}