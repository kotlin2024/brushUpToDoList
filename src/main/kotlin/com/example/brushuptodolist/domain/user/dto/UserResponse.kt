package com.example.brushuptodolist.domain.user.dto

data class UserResponse(
    val userName: String,
    val userEmail: String,
    val userRole: UserRole
)
