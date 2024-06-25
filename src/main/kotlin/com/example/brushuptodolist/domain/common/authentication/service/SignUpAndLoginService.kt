package com.example.brushuptodolist.domain.common.authentication.service

import com.example.brushuptodolist.domain.common.authentication.dto.LoginRequest
import com.example.brushuptodolist.domain.common.authentication.jwt.JwtTokenManager
import com.example.brushuptodolist.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpAndLoginService(
    private val jwtTokenManager: JwtTokenManager,
    private val userRepository: UserRepository
) {

    @Transactional
    fun signUp():String{





        return "정상적으로 회원가입이 완료되었습니다"
    }

    @Transactional
    fun login(loginRequest: LoginRequest): String{

        TODO("아이디 비번이 일치하지 않으면 예외 처리")
//        return jwtTokenManager.generateToken()
    }
}