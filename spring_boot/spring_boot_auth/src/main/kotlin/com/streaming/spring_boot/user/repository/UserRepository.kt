package com.streaming.spring_boot.user.repository

import com.streaming.spring_boot.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

}