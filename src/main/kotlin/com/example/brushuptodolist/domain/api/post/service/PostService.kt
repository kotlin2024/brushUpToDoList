package com.example.brushuptodolist.domain.api.post.service

import com.example.brushuptodolist.domain.api.post.dto.PostResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping

@Service
class PostService {


    fun readAllPost():List<PostResponse> {
        TODO()
    }

    fun readPostById(postId: Long): PostResponse{
        TODO()
    }

    @Transactional
    fun createPost(): String{
        TODO()
    }

    @Transactional
    fun updatePost(postId: Long): String {
        TODO()
    }

    @Transactional
    fun deletePost(postId: Long): String {

        TODO()
    }
}