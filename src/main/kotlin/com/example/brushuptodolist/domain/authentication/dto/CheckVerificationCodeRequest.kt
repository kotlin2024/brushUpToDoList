package com.example.brushuptodolist.domain.authentication.dto

data class CheckVerificationCodeRequest (
    val email:String,
    val verificationCode: String
)