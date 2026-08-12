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
    val password: String
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
    //val tokens: TokenPair,
    //val userInformation: UserInformation

    val access_token: String,
    val refresh_token: String
)

@Serializable
data class ProfileImageRequest(
    val email: String,
    val profileImage: String?
)

@Serializable
data class SearchRequest(
    val email: String
)

@Serializable
data class MyListRequest(
    val email: String,
    val type: ListType
)

@Serializable
data class AddMyListRequest(
    val movieId: String,
    val email: String,
    val type: ListType
)