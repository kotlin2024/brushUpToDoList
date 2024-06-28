package com.example.brushuptodolist.domain.api.comment.dto


data class CommentPageResponse<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
    val isLast: Boolean,
)
