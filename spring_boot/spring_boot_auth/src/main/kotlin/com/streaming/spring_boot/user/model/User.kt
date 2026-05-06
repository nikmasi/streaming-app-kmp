package com.streaming.spring_boot.user.model

import jakarta.persistence.*
import java.time.Instant
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users") //reserved User
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(name = "\"hashedPassword\"", nullable = false)
    var hashedPassword: String,
    val createdAt: Instant = Instant.now(),

    @Column(nullable = false, name = "\"firstname\"")
    val firstname: String,

    @Column(nullable = false, name = "\"lastname\"")
    val lastname: String,

    @Column(nullable = false, name = "\"phone\"")
    val phone: String,

    @Enumerated(EnumType.STRING)
    val role: Role,

    @Column(nullable = false, name = "\"profileImage\"")
    var profileImage: String? = null

): UserDetails{
    override fun getAuthorities(): Collection<GrantedAuthority> = role.authorities

    override fun getPassword(): String = hashedPassword

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}