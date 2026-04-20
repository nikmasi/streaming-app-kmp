package com.streaming.spring_boot.security


import com.streaming.spring_boot.user.model.Search
import com.streaming.spring_boot.user.repository.SearchRepository
import com.streaming.spring_boot.user.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val searchRepository: SearchRepository
) {

    @Transactional
    fun updateProfileImage(userEmail: String, profileImage: String?): Boolean {
        val user = userRepository.findByEmail(userEmail.trim())
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")

        user.profileImage = profileImage
        userRepository.save(user)

        return true
    }

    @Transactional
    fun saveSearchContent(title: String, email: String){
        val user = userRepository.findByEmail(email.trim())
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")

        val existing = searchRepository.findByIdUserAndQuery(user.id, title)

        if (existing != null) {
            existing.count++
            existing.updatedAt = LocalDateTime.now()
            searchRepository.save(existing)
        } else {
            searchRepository.save(
                Search(
                    idUser = user.id,
                    query = title,
                    count = 1,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )
            )
        }
    }

    fun findTop10SearchContent(email: String): List<Search> {
        val user = userRepository.findByEmail(email.trim())
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")

        return searchRepository.findTop10ByIdUserOrderByCreatedAtDesc(user.id)
    }
}