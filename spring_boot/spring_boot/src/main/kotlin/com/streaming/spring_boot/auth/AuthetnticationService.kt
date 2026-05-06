package com.streaming.spring_boot.auth

import com.streaming.spring_boot.config.JwtService
import com.streaming.spring_boot.token.Token
import com.streaming.spring_boot.token.TokenRepository
import com.streaming.spring_boot.token.TokenType
import com.streaming.spring_boot.user.model.User
import com.streaming.spring_boot.user.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tools.jackson.databind.ObjectMapper
import java.io.IOException

@Service
class AuthenticationService(
    private val repository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(request: RegisterRequest): AuthenticationResponse {
        val user = User(
            email = request.email,
            role = request.role,
            id = 1,
            hashedPassword = passwordEncoder.encode(request.password) ?: "",
            fullName = request.firstname + " "+ request.lastname,
            phone = "03254324141",
            profileImage = ""
        )
        val savedUser = repository.save(user)
        val jwtToken = jwtService.generateToken(savedUser)
        val refreshToken = jwtService.generateRefreshToken(savedUser)

        saveUserToken(savedUser, jwtToken)

        return AuthenticationResponse(
            accessToken = jwtToken,
            refreshToken = refreshToken
        )
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )

        val user = repository.findByEmail(request.email)  ?: throw UsernameNotFoundException("User not found")
           // .orElseThrow { NoSuchElementException("User not found") }

        val jwtToken = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)

        revokeAllUserTokens(user)
        saveUserToken(user, jwtToken)

        return AuthenticationResponse(
            accessToken = jwtToken,
            refreshToken = refreshToken
        )
    }

    private fun saveUserToken(user: User, jwtToken: String) {
        val token = Token(
            user = user,
            token = jwtToken,
            tokenType = TokenType.BEARER,
            expired = false,
            revoked = false
        )
        tokenRepository.save(token)
    }

    private fun revokeAllUserTokens(user: User) {
        val validUserTokens = tokenRepository.findAllValidTokenByUser(user.id!!)
        if (validUserTokens.isEmpty()) return

        validUserTokens.forEach { token ->
            token.expired = true
            token.revoked = true
        }
        tokenRepository.saveAll(validUserTokens)
    }

    @Throws(IOException::class)
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }

        val refreshToken = authHeader.substring(7)
        val userEmail = jwtService.extractUsername(refreshToken)

        if (userEmail != null) {
            //val user = repository.findByEmail(userEmail).orElseThrow()
            val user =repository.findByEmail(userEmail) ?: throw UsernameNotFoundException("User not found")

            if (jwtService.isTokenValid(refreshToken, user)) {
                val accessToken = jwtService.generateToken(user)
                revokeAllUserTokens(user)
                saveUserToken(user, accessToken)

                val authResponse = AuthenticationResponse(
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
                ObjectMapper().writeValue(response.outputStream, authResponse)
            }
        }
    }
}