package org.streaming.app.networking.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var email: String,
    val hashedPassword: String,
    //val createdAt: Long,
    var fullName: String,
    var phone: String,
    var profileImage: String?
)

@Serializable
data class LoginRequest(
    val email: String,
    val hashedPassword: String
)

@Serializable
data class SignUpRequest(
    val email: String,
    val hashedPassword: String,
    val fullName: String,
    val phone: String
)

@Serializable
data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class UserInformation(
    val email: String,
    val fullName: String,
    val phone: String,
    val profileImage: String?
)

@Serializable
data class AuthResponse(
    val tokens: TokenPair,
    val userInformation: UserInformation
)

@Serializable
data class ProfileImageRequest(
    val email: String,
    val profileImage: String?
)