package com.example.brushuptodolist.domain.user.repository

import com.example.brushuptodolist.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
}