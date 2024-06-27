package com.example.brushuptodolist.domain.api.post.entity

import com.example.brushuptodolist.domain.api.post.dto.PostResponse
import com.example.brushuptodolist.domain.user.entity.User
import jakarta.persistence.*
import java.time.LocalDateTime


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

    @Column(name ="created_at")
    var createdAt: LocalDateTime? = null

//    @Column(name ="updated_at")
//    var updatedAt: LocalDateTime? = null

    @PrePersist
    fun prePersist(){
        createdAt = LocalDateTime.now()
//        updatedAt = null
    }

//    @PreUpdate
//    fun preUpdate(){
//        updatedAt = LocalDateTime.now()
//    }

}

fun Post.toResponse(): PostResponse =
    PostResponse(
        postId = postId!!,
        postUserName = postUserName,
        title = title,
        description = description,
        createdAt =  createdAt!!,
//        updatedAt = updatedAt
    )