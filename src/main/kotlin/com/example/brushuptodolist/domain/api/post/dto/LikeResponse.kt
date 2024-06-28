package com.example.brushuptodolist.domain.api.post.dto

data class LikeResponse(
    val postId: Long,
    val postTitle: String,
    val userId: Long,
    val userName: String,
    val likesOrNot:Boolean,
)