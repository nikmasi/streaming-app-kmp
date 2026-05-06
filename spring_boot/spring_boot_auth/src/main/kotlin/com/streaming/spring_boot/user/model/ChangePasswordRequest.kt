package com.streaming.spring_boot.user.model

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String,
    val confirmationPassword: String
)