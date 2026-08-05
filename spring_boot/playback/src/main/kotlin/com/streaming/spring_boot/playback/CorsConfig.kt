package com.streaming.spring_boot.playback

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()

        config.allowedOrigins = listOf("http://localhost:4200")

        config.allowedMethods = listOf(
            "GET",
            "POST",
            "OPTIONS"
        )

        config.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()

        source.registerCorsConfiguration(
            "/**",
            config
        )

        return CorsFilter(source)
    }
}
