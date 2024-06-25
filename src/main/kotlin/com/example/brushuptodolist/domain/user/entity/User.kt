package com.example.brushuptodolist.domain.user.entity

import com.example.brushuptodolist.domain.user.dto.UserResponse
import jakarta.persistence.*


@Entity
@Table(name = "users")
class User(

    @Column(name = "user_name")
    val username: String,

    @Column(name="user_email")
    val userEmail: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? =null

){
    fun User.toResponse(): UserResponse= UserResponse(
        username = username,
        userEmail= userEmail
    )

}