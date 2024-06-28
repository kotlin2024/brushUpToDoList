package com.example.brushuptodolist.domain.api.comment.dto

import java.time.LocalDateTime

data class CommentResponse(

    val postId: Long,
    val commentId: Long,
    val commenterName: String,
    val commentDescription: String,
    val commentCreatedAt: String,
    var commentUpdatedAt: String?
)
