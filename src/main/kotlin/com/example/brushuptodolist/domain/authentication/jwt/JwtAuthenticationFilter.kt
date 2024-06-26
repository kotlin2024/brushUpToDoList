package com.example.brushuptodolist.domain.authentication.jwt


import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter(
    private val jwtTokenManager: JwtTokenManager,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var pureToken:String? =null

        if(request.getHeader(AUTHORIZATION)!=null && request.getHeader(AUTHORIZATION).startsWith("Bearer ")){
            pureToken= request.getHeader("Authorization").substring(7)
        }

        if(pureToken!= null){
            jwtTokenManager.validateToken(pureToken).onSuccess {



                val userEmail = it.payload.subject
                val userName = it.payload["userName"] as String
                val userRole= it.payload["userRole"] as String

                val userPrincipal= UserPrincipal(userName= userName, userRole= setOf(userRole) , userEmail = userEmail)
                val authentication = JwtAuthenticationToken(
                    userPrincipal= userPrincipal,
                    details = WebAuthenticationDetailsSource()
                        .buildDetails(request)
                )
                SecurityContextHolder.getContext().authentication = authentication
            }.onFailure{
                logger.debug("Token validation failed", it)
            }

        }
        filterChain.doFilter(request, response)

    }

}