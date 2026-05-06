package com.streaming.spring_boot.user.model

import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class Role {
    USER,
    ADMIN;

    val authorities: List<SimpleGrantedAuthority>
        get() = listOf(SimpleGrantedAuthority("ROLE_${this.name}"))
}