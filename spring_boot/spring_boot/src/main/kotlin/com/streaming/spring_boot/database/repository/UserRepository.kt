package com.streaming.spring_boot.database.repository

import com.streaming.spring_boot.database.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

}