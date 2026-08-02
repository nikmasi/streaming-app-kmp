package com.streaming.spring_boot.user

import com.streaming.spring_boot.user.model.ChangePasswordRequest
import com.streaming.spring_boot.user.model.ProfileRequest
import com.streaming.spring_boot.user.model.ProfileResponse
import com.streaming.spring_boot.user.model.User
import com.streaming.spring_boot.user.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
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

    fun profile(request: ProfileRequest): ProfileResponse {
        val user = repository.findByEmail(request.email)  ?: throw UsernameNotFoundException("User not found")

        return ProfileResponse(
            firstname = user.firstname,
            lastname = user.lastname,
            email = user.email,
            role = user.role,
            profileImage = user.profileImage
        )
    }

    fun editProfile(request: ProfileResponse): ProfileResponse {

        val user = repository.findByEmail(request.email)
            ?: throw UsernameNotFoundException("User not found")

        val updatedUser = user.copy(
            firstname = request.firstname,
            lastname = request.lastname,
            profileImage = request.profileImage
        )

        repository.save(updatedUser)

        return ProfileResponse(
            firstname = updatedUser.firstname,
            lastname = updatedUser.lastname,
            email = updatedUser.email,
            role = updatedUser.role,
            profileImage = updatedUser.profileImage
        )
    }

    fun getAllUsers(): List<ProfileResponse> {

        return repository.findAll()
            .map { user ->
                ProfileResponse(
                    firstname = user.firstname,
                    lastname = user.lastname,
                    email = user.email,
                    role = user.role,
                    profileImage = user.profileImage
                )
            }
    }

}