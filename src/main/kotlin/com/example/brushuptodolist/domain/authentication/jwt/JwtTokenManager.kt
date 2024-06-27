package com.example.brushuptodolist.domain.authentication.jwt

import com.example.brushuptodolist.domain.user.dto.UserRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class JwtTokenManager(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
) {

    fun generateToken(userEmail: String, userName: String, userRole: UserRole): String {

        val claims = Jwts.claims().add(mapOf("userEmail" to userEmail, "userName" to userName, "userRole" to userRole)).build()

        val key =  Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

        val expirationTimeInMillis = 24 * 60 * 60 * 1000

        return Jwts.builder()
            .claims(claims)
            .subject(userEmail)
            .issuer(issuer)
            .expiration(Date(System.currentTimeMillis() + expirationTimeInMillis))
            .signWith(key).compact()

    }

    fun validateToken(token: String): Result<Jws<Claims>> {

        return kotlin.runCatching {
            val key= Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
        }
    }
}