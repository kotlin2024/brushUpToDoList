package com.example.brushuptodolist.domain.authentication.controller

import com.example.brushuptodolist.domain.authentication.dto.CheckingUserNameRequest
import com.example.brushuptodolist.domain.authentication.dto.LoginRequest
import com.example.brushuptodolist.domain.authentication.dto.SignUpRequest
import com.example.brushuptodolist.domain.authentication.service.SignUpAndLoginService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authentication")
class SignUpAndLoginController(
    private val signUpAndLoginService: SignUpAndLoginService,
){


    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.OK).body(signUpAndLoginService.signUp(signUpRequest))


    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): String =
        signUpAndLoginService.login(loginRequest)


    @PostMapping("/check-duplicated-userName")
    fun checkingDuplicatedUserName(@RequestBody checkingUserNameRequest: CheckingUserNameRequest): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.OK).body(signUpAndLoginService.checkingDuplicateUserName(checkingUserNameRequest))
}