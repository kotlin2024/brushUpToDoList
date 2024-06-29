package com.example.brushuptodolist.domain.authentication.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService @Autowired constructor(
    private val mailSender: JavaMailSender
) {

    fun sendVerificationCode(to: String, code: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(to)
        helper.setSubject("인증번호 입니다")
        helper.setText("인증번호: $code", true) // true로 설정하여 HTML 내용을 허용

        mailSender.send(message)
    }
}