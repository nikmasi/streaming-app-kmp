package com.streaming.spring_boot.user

import com.streaming.spring_boot.user.model.ChangePasswordRequest
import com.streaming.spring_boot.user.model.User
import com.streaming.spring_boot.user.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val repository: UserRepository
) {

    fun changePassword(request: ChangePasswordRequest, connectedUser: Principal) {

        val user = (connectedUser as UsernamePasswordAuthenticationToken).principal as User

        if (!passwordEncoder.matches(request.currentPassword, user.password)) {
            throw IllegalStateException("Wrong password")
        }

        if (request.newPassword != request.confirmationPassword) {
            throw IllegalStateException("Passwords are not the same")
        }

        user.hashedPassword = passwordEncoder.encode(request.newPassword).toString()

        repository.save(user)
    }
}