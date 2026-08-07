package com.streaming.spring_boot.config


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function

@Service
class JwtService {
    @Value("\${application.security.jwt.secret-key}")
    private lateinit var secretKey: String

    @Value("\${application.security.jwt.expiration}")
    private var jwtExpiration: Long = 0

    @Value("\${application.security.jwt.refresh-token.expiration}")
    private var refreshExpiration: Long = 0

    fun extractUsername(token: String): String? {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    /*
        access_token will have sub, role, authorities, iat and exp;
        example:
        {
          "sub": "admin@test.com",
          "role": "ADMIN",
          "authorities": ["ROLE_ADMIN"]  //for spring security @PreAuthorize("hasRole('ADMIN')")...
          "iat": 1786107330,
          "exp": 1786193730
        }
     */
    fun generateToken(userDetails: UserDetails): String {
        val claims = mutableMapOf<String, Any>()

        val user = userDetails as com.streaming.spring_boot.user.model.User
        claims["role"] = user.role.name
        claims["authorities"] = user.authorities.map { it.authority }

        return generateToken(claims, userDetails)
    }

    fun generateToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails
    ): String {
        return buildToken(extraClaims, userDetails, jwtExpiration)
    }

    fun generateRefreshToken(userDetails: UserDetails): String {
        return buildToken(HashMap(), userDetails, refreshExpiration)
    }

    private fun buildToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails,
        expiration: Long
    ): String {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSignInKey() as javax.crypto.SecretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}