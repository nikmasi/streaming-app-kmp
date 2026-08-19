package com.streaming.spring_boot.gateway.security

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtGatewayFilter(
    private val jwtService: JwtService
) : GlobalFilter, Ordered {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val path = request.uri.path

        // oslobodi rute koje ne zahtevaju autentifikaciju auth i catalog
        if (path.contains("/api/v1/auth") || path.contains("/api/v1/catalog")) {
            return chain.filter(exchange)
        }

        val authHeader = request.headers.getFirst("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorizedResponse(exchange)
        }

        val token = authHeader.substring(7)

        if (!jwtService.isTokenValid(token)) {
            return unauthorizedResponse(exchange)
        }

        // izvlacimo email i prosledjujemo mikroservisima
        val userEmail = jwtService.extractUsername(token)
        val mutatedRequest = request.mutate()
            .header("X-User-Email", userEmail ?: "")
            .build()

        val mutatedExchange = exchange.mutate().request(mutatedRequest).build()

        return chain.filter(mutatedExchange)
    }

    private fun unauthorizedResponse(exchange: ServerWebExchange): Mono<Void> {
        val response = exchange.response
        response.statusCode = HttpStatus.UNAUTHORIZED

        // dodajemo CORS zahglavlja i na 401 odgovor da ga pregledac ne blokira
        response.headers.add("Access-Control-Allow-Origin", "http://localhost:4200")
        response.headers.add("Access-Control-Allow-Credentials", "true")
        response.headers.add("Access-Control-Allow-Headers", "*")

        return response.setComplete()
    }

    override fun getOrder(): Int = -1
}