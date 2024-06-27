package com.example.brushuptodolist.domain.api.comment.repository

import com.example.brushuptodolist.domain.api.comment.entity.Comment
import com.example.brushuptodolist.domain.api.post.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {

    fun findAllByPost(post: Post): List<Comment>

    fun findByCommentId( commentId: Long): Comment

}