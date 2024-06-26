package com.example.brushuptodolist.domain.authentication.jwt

import com.example.brushuptodolist.domain.user.dto.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val userName:String,
    val userEmail:String,
    val authorities: Collection<GrantedAuthority>
){

    constructor(userName:String, userEmail:String,userRole: Set<String>) : this(
        userName,
        userEmail,
        userRole.map{ SimpleGrantedAuthority("ROLE_$it")}
    )
}