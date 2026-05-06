package com.streaming.spring_boot.auth

data class AuthenticationRequest(
    val email: String,
    val password: String
)