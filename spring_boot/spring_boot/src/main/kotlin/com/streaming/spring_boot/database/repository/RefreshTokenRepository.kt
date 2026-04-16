package com.streaming.spring_boot.database.repository

import com.streaming.spring_boot.database.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun findByUserIdAndHashedToken(userId: Long, hashedToken: String): RefreshToken?
    fun deleteByUserIdAndHashedToken(userId: Long, hashedToken: String)
}