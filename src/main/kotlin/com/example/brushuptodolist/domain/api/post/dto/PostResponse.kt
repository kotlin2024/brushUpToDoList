package com.example.brushuptodolist.domain.api.post.dto

import java.time.LocalDateTime

data class PostResponse(

    val postId: Long,
    val postUserName:String,
    val title:String,
    val description:String,
    val createdAt: String,
    var updatedAt: String?,
    var likes:Long,
)