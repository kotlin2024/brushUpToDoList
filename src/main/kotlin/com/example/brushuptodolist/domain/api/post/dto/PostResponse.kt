package com.example.brushuptodolist.domain.api.post.dto

data class PostResponse(

    val postId: Long,
    val postUserName:String,
    val title:String,
    val description:String,
)