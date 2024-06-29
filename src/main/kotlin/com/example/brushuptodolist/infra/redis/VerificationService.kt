package com.example.brushuptodolist.infra.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class VerificationService {

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>

    fun saveVerificationCode(email: String, code: String) {
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES) // 5분 동안 유효
    }

    fun getVerificationCode(email: String): String? {
        return redisTemplate.opsForValue().get(email)
    }
}