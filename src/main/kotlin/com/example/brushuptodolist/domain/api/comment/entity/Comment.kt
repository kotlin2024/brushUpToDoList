package com.example.brushuptodolist.domain.api.comment.entity

import com.example.brushuptodolist.domain.api.comment.dto.CommentResponse
import com.example.brushuptodolist.domain.api.post.entity.Post
import com.example.brushuptodolist.domain.user.entity.User
import jakarta.persistence.*

import java.time.LocalDateTime


@Entity
@Table(name = "comments")
class Comment(

    @Column(name= "commenter_name")
    val commenterName: String,

    @Column(name = "description")
    var commentDescription: String,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    val post: Post


) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var commentId: Long? =null

    @Column(name= "comment_created_at")
    var commentCreatedAt: LocalDateTime? = null

    @PrePersist
    fun prePersist(){
        commentCreatedAt = LocalDateTime.now()
    }
}

fun Comment.toResponse(): CommentResponse = CommentResponse(
    postId = post.postId!!,
    commentId = commentId!!,
    commenterName = commenterName,
    commentDescription = commentDescription,
    commentCreatedAt = commentCreatedAt!!,

)