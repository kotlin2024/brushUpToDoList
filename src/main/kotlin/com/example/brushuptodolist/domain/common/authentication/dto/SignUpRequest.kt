package com.example.brushuptodolist.domain.common.authentication.dto

import jakarta.validation.constraints.Size
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignUpRequest(

 //   @field:Pattern(regexp = "^[a-zA-Z0-9]{3,}$", message = "닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하세요")
    val userName: String,

//    @field:Email(message = "올바른 이메일 양식을 입력하세요")
    val userEmail: String,

 //   @field:Size(min = 4, message = "비밀번호는 최소 4자 이상 입력하세요")
    val userPassword: String,
)