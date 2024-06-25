package com.example.brushuptodolist.domain.common.authentication.controller

import com.example.brushuptodolist.domain.common.authentication.dto.LoginRequest
import com.example.brushuptodolist.domain.common.authentication.dto.SignUpRequest
import com.example.brushuptodolist.domain.common.authentication.service.SignUpAndLoginService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/authentication")
class SignUpAndLoginController(
    private val signUpAndLoginService: SignUpAndLoginService,
){


    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.OK).body(signUpAndLoginService.signUp(signUpRequest))


    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): String =
        signUpAndLoginService.login(loginRequest)
}