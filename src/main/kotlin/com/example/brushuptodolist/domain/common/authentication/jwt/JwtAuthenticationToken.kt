package com.example.brushuptodolist.domain.common.authentication.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetails

class JwtAuthenticationToken(
    private val userPrincipal: UserPrincipal,
    details: WebAuthenticationDetails
): AbstractAuthenticationToken(userPrincipal.authorities) {

    init{
        super.setAuthenticated(true) // 이토큰이 이미 인증된 상태
        super.setDetails(details) // 요청의 추가정보 설정
    }

    override fun getCredentials() = null

    override fun getPrincipal() = userPrincipal

    override fun isAuthenticated():Boolean = true


}