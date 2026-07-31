package com.streaming.spring_boot.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class SecurityConfig {

//    @Bean
//    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
//        return http
//            .csrf { it.disable() }
//            .cors { }
//            .authorizeExchange {
//                it.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                it.pathMatchers("/api/v1/auth/**").permitAll()
//                it.pathMatchers("/api/v1/catalog/**").permitAll()
//                it.anyExchange().authenticated()
//            }
//            .build()
//    }

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .cors { }
            .authorizeExchange {
                it.anyExchange().permitAll()
            }
            .build()
    }

}