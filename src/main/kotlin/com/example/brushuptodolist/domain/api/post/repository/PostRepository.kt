package com.example.brushuptodolist.domain.api.post.repository

import com.example.brushuptodolist.domain.api.post.entity.Post
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post,Long> {

    fun findByPostId(postId: Long): Post?

    fun findAllByOrderByCreatedAtDesc(pageable: Pageable) : List<Post>

}