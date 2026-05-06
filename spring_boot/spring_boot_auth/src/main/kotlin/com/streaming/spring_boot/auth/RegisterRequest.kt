package com.streaming.spring_boot.auth

import com.streaming.spring_boot.user.model.Role

data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val role: Role,
    val profileImage: String?
)