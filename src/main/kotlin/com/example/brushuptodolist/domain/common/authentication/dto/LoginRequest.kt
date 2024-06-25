package com.example.brushuptodolist.domain.common.authentication.dto

data class LoginRequest(
    val userEmail: String,
    val userPassword: String,
)
