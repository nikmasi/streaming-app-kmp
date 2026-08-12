package org.streaming.app.networking.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileRequest(
    val email: String
)


@Serializable
data class ProfileResponse(
    val firstname: String,
    val lastname: String,
    val email: String,
    val profileImage: String,
    val role: String
)