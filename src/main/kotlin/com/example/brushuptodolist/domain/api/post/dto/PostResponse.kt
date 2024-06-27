package com.example.brushuptodolist.domain.api.post.dto

import java.time.LocalDateTime

data class PostResponse(

    val postId: Long,
    val postUserName:String,
    val title:String,
    val description:String,
    val createdAt: LocalDateTime,
//    var updatedAt: LocalDateTime,
)