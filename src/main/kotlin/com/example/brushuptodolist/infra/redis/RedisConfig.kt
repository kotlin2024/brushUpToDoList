package com.example.brushuptodolist.infra.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories
class RedisConfig {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        // Redis Labs에서 제공하는 Redis 인스턴스의 호스트와 포트 번호
        val host = "redis-13975.c290.ap-northeast-1-2.ec2.redns.redis-cloud.com"
        val port = 13975

        // Redis Labs에서 제공하는 인증 비밀번호
        val password = "0Ht4Nkplqe1MXVpu1ygO9ibNNg6B5wMU"

        return LettuceConnectionFactory(host, port).apply {
            setPassword(password)
        }
    }

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.setConnectionFactory(connectionFactory)
        return template
    }
}
