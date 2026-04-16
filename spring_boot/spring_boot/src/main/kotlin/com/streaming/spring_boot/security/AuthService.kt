package com.streaming.spring_boot.security

import com.streaming.spring_boot.database.model.RefreshToken
import com.streaming.spring_boot.database.model.User
import com.streaming.spring_boot.database.repository.RefreshTokenRepository
import com.streaming.spring_boot.database.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.security.MessageDigest
import java.util.Base64

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val hashEncoder: HashEncoder,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    data class TokenPair(
        val accessToken: String,
        val refreshToken: String
    )

    data class UserInformation(
        val email: String,
        val fullName: String,
        val phone: String,
        val profileImage: String?
    )

    data class AuthResponse(
        val tokens: TokenPair,
        val userInformation: UserInformation
    )

    fun register(email: String, password: String, fullName:String, phone:String): AuthResponse {
        val user = userRepository.findByEmail(email.trim())
        if(user != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "A user with that email already exists.")
        }
        userRepository.save(
            User(
                email = email,
                hashedPassword = hashEncoder.encode(raw=password),
                fullName = fullName,
                phone = phone
            )
        )
        return this.login(email,password)
    }

    fun login(email: String, password: String): AuthResponse {
        val user = userRepository.findByEmail(email)
            ?: throw BadCredentialsException("Invalid credentials.")

        if(!hashEncoder.matches(password, user.hashedPassword)) {
            throw BadCredentialsException("Invalid credentials.")
        }

        val newAccessToken = jwtService.generateAccessToken(user.id.toHexString())
        val newRefreshToken = jwtService.generateRefreshToken(user.id.toHexString())

        storeRefreshToken(user.id, newRefreshToken)

        return AuthResponse(
            tokens= TokenPair(
                accessToken = newAccessToken,
                refreshToken = newRefreshToken
            ),
            userInformation = UserInformation(user.email,user.fullName,user.phone, user.profileImage)
        )
    }

    @Transactional
    fun refresh(refreshToken: String): TokenPair {
        if(!jwtService.validateRefreshToken(refreshToken)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token.")
        }

        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(userId.toLong()).orElseThrow {
            ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token.")
        }

        val hashed = hashToken(refreshToken)
        refreshTokenRepository.findByUserIdAndHashedToken(user.id, hashed)
            ?: throw ResponseStatusException(
                HttpStatusCode.valueOf(401),
                "Refresh token not recognized (maybe used or expired?)"
            )

        refreshTokenRepository.deleteByUserIdAndHashedToken(user.id, hashed)

        val newAccessToken = jwtService.generateAccessToken(userId)
        val newRefreshToken = jwtService.generateRefreshToken(userId)

        storeRefreshToken(user.id, newRefreshToken)

        return TokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    private fun storeRefreshToken(userId: Long, rawRefreshToken: String) {
        val hashed = hashToken(rawRefreshToken)
        val expiryMs = jwtService.refreshTokenValidityMs
        val expiresAt = Instant.now().plusMillis(expiryMs)

        refreshTokenRepository.save(
            RefreshToken(
                userId = userId,
                expiresAt = expiresAt,
                hashedToken = hashed
            )
        )
    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}