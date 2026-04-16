package com.streaming.spring_boot.security


import com.streaming.spring_boot.database.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun updateProfileImage(userEmail: String, profileImage: String?): Boolean {
        val user = userRepository.findByEmail(userEmail.trim())
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")

        user.profileImage = profileImage
        userRepository.save(user)

        return true
    }
}