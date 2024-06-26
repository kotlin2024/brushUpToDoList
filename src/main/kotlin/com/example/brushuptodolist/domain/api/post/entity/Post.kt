package com.example.brushuptodolist.domain.api.post.entity

import com.example.brushuptodolist.domain.api.post.dto.PostResponse
import com.example.brushuptodolist.domain.user.entity.User
import jakarta.persistence.*


@Entity
@Table(name ="post_table")
class Post(

    @Column(name="post_title")
    var title: String,

    @Column(name="post_description")
    var description: String,

    @Column(name="post_user_name")
    val postUserName: String,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User


){
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var postId: Long? = null

}

fun Post.toResponse(): PostResponse =
    PostResponse(
        postId = postId!!,
        postUserName = postUserName,
        title = title,
        description = description,
    )