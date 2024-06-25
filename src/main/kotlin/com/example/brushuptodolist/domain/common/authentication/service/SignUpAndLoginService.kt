package com.example.brushuptodolist.domain.common.authentication.service

import com.example.brushuptodolist.domain.common.authentication.dto.LoginRequest
import com.example.brushuptodolist.domain.common.authentication.dto.SignUpRequest
import com.example.brushuptodolist.domain.common.authentication.jwt.JwtTokenManager
import com.example.brushuptodolist.domain.user.dto.UserRole
import com.example.brushuptodolist.domain.user.entity.User
import com.example.brushuptodolist.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpAndLoginService(
    private val jwtTokenManager: JwtTokenManager,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun signUp(signUpRequest: SignUpRequest):String{

        if(userRepository.existsByUserEmail(signUpRequest.userEmail)) throw RuntimeException("이미 존재하는 이메일입니다.") //TODO 예외처리하기

        userRepository.save(
            User(
                username = signUpRequest.userName,
                userEmail = signUpRequest.userEmail,
                userPassword = passwordEncoder.encode(signUpRequest.userPassword)
            )

        )
        return "정상적으로 회원가입이 완료되었습니다"
    }

    @Transactional
    fun login(loginRequest: LoginRequest): String{

        val loginUser= userRepository.findByUserEmail(loginRequest.userEmail)?: throw RuntimeException("해당 이메일은 존재하지 않습니다")
        if(!passwordEncoder.matches(loginRequest.userPassword, loginUser.userPassword)) throw RuntimeException("비밀번호가 틀립니다!")

        return jwtTokenManager.generateToken(userEmail =loginUser.userEmail , userName =loginUser.username , userRole = UserRole.BASIC)
    }
}