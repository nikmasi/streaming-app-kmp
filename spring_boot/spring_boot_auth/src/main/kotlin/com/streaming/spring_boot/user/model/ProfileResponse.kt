package com.streaming.spring_boot.user.model

data class ProfileResponse(
    val firstname: String,
    val lastname: String,
    val email: String,
    val role: Role,
    val profileImage: String?
)