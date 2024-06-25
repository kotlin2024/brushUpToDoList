package com.example.brushuptodolist.domain.common.authentication.jwt

import com.example.brushuptodolist.domain.user.dto.UserRole
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class jwtTokenManager(
    @Value("\${auth.jwt.issuer") private val issuer:String,
    @Value("\${auth.jwt.secret}") private val secret:String,
) {

    fun generateToken(userEmail: String, userName: String, userRole: UserRole): String {

        val claims = Jwts.claims().add(mapOf("userEmail" to userEmail, "userName" to userName, "userRole" to userRole))

        val key =  Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder().subject(userEmail).issuer(issuer).expiration(Date(System.currentTimeMillis() + 3600 * 24)).signWith(key).compact()

    }
}