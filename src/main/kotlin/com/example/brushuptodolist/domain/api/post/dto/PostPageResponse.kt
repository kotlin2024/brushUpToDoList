package com.example.brushuptodolist.domain.api.post.dto

data class PostPageResponse<T>(

    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
    val isLast: Boolean,
)
