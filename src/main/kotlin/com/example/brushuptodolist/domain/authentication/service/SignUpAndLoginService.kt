package com.example.brushuptodolist.domain.authentication.service

import com.example.brushuptodolist.domain.authentication.dto.CheckingUserNameRequest
import com.example.brushuptodolist.domain.authentication.dto.LoginRequest
import com.example.brushuptodolist.domain.authentication.dto.SignUpRequest
import com.example.brushuptodolist.domain.authentication.jwt.JwtTokenManager
import com.example.brushuptodolist.domain.user.dto.UserRole
import com.example.brushuptodolist.domain.user.entity.User
import com.example.brushuptodolist.domain.user.repository.UserRepository
import com.example.brushuptodolist.infra.aop.ValidationSignUp
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpAndLoginService(
    private val jwtTokenManager: JwtTokenManager,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @ValidationSignUp
    @Transactional
    fun signUp(signUpRequest: SignUpRequest):String{

        if(userRepository.existsByUserEmail(signUpRequest.userEmail)) throw RuntimeException("이미 존재하는 이메일입니다.") //TODO 예외처리하기

        userRepository.save(
            User(
                userName = signUpRequest.userName,
                userEmail = signUpRequest.userEmail,
                userPassword = passwordEncoder.encode(signUpRequest.userPassword),
                userRole = UserRole.BASIC
            )

        )
        return "정상적으로 회원가입이 완료되었습니다"
    }

    @Transactional
    fun login(loginRequest: LoginRequest): String{

        val loginUser= userRepository.findByUserEmail(loginRequest.userEmail)?: throw RuntimeException("해당 이메일은 존재하지 않습니다")
        if(!passwordEncoder.matches(loginRequest.userPassword, loginUser.userPassword)) throw RuntimeException("비밀번호가 틀립니다!")

        return jwtTokenManager.generateToken(userEmail =loginUser.userEmail , userName =loginUser.userName , userRole = loginUser.userRole)
    }


    fun checkingDuplicateUserName(checkingUserNameRequest: CheckingUserNameRequest): String{
        if(userRepository.existsByUserName(checkingUserNameRequest.userName)){
            return "이미 존재하는 유저이름 입니다"
        }
        else return "사용 가능한 유저이름 입니다!!!"

    }
}