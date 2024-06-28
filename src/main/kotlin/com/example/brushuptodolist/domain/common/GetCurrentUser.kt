package com.example.brushuptodolist.domain.common

import com.example.brushuptodolist.domain.authentication.jwt.UserPrincipal
import com.example.brushuptodolist.domain.user.entity.User
import com.example.brushuptodolist.domain.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component


@Component
class GetCurrentUser(
    private val userRepository: UserRepository
) {

    fun getCurrentUser(): User?{

        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        return userRepository.findByUserEmail(userPrincipal.userEmail)

    }
}