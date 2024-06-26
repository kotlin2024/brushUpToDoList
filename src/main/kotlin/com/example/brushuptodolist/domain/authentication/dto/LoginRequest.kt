package com.example.brushuptodolist.domain.authentication.dto

data class LoginRequest(
    val userEmail: String,
    val userPassword: String,
)
