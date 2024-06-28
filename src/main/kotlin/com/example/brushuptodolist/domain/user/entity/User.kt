package com.example.brushuptodolist.domain.user.entity

import com.example.brushuptodolist.domain.api.comment.entity.Comment
import com.example.brushuptodolist.domain.api.post.entity.Like
import com.example.brushuptodolist.domain.user.dto.UserResponse
import com.example.brushuptodolist.domain.user.dto.UserRole
import jakarta.persistence.*


@Entity
@Table(name = "users")
class User(

    @Column(name = "user_name")
    val userName: String,

    @Column(name="user_email")
    val userEmail: String,

    @Column(name="user_password")
    val userPassword: String,

    @Enumerated(EnumType.STRING)
    @Column(name ="user_role")
    val userRole: UserRole,



    ){
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? =null


}
fun User.toResponse(): UserResponse= UserResponse(
    userName = userName,
    userEmail= userEmail,
    userRole = userRole
)
