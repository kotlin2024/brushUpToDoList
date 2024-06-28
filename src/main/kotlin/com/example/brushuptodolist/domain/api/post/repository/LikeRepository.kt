package com.example.brushuptodolist.domain.api.post.repository

import com.example.brushuptodolist.domain.api.post.entity.Like
import com.example.brushuptodolist.domain.api.post.entity.Post
import com.example.brushuptodolist.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like,Long>{

    fun findByUserAndPost(user: User, post: Post): Like
}