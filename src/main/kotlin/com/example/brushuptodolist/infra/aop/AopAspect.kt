package com.example.brushuptodolist.infra.aop

import com.example.brushuptodolist.domain.api.post.dto.UpdatePostRequest
import com.example.brushuptodolist.domain.authentication.dto.SignUpRequest
import com.example.brushuptodolist.domain.user.repository.UserRepository
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component



@Aspect
@Component
class AopAspect(
    private val userRepository: UserRepository
) {

    @Before("@annotation(com.example.brushuptodolist.infra.aop.ValidationSignUp)")
    fun validationSignUpAspect(joinPoint: JoinPoint) {
        val args = joinPoint.args

        val signUpRequest = args.find { it is SignUpRequest } as SignUpRequest

        if (signUpRequest.userPassword == signUpRequest.userEmail) {
            throw RuntimeException("유저 비밀번호와 이메일이 같아서는 안됩니다") // TODO: 예외처리
        }

        if(signUpRequest.userPassword != signUpRequest.checkingPassword){
            throw RuntimeException("처음에 설정한 비밀번호와 다릅니다!!") // TODO: 예외처리
        }

        if(userRepository.existsByUserName(signUpRequest.userName)){
            throw RuntimeException("이미 존재하는 유저 이름 입니다!!")
        }

    }


    @Before("@annotation(com.example.brushuptodolist.infra.aop.ValidationPost)")
    fun validationPostAspect(joinPoint: JoinPoint){

        val args = joinPoint.args

        val updatePostRequest = args.find {it is UpdatePostRequest} as UpdatePostRequest

        if(updatePostRequest.title.isEmpty() || updatePostRequest.description.isEmpty()){
            throw RuntimeException("제목이나 내용에 공백이 있으면 안됩니다.")
        }


        if( updatePostRequest.title.length > 500) {
            throw RuntimeException("제목은 500자 이하로 입력해주세요.")
        }

        if(updatePostRequest.description.length > 5000){
            throw RuntimeException("내용은 5000자 이하로 작성해주세요.")
        }

    }

}