package com.example.brushuptodolist.domain.api.post.entity

import com.example.brushuptodolist.domain.api.comment.entity.Comment
import com.example.brushuptodolist.domain.api.post.dto.PostResponse
import com.example.brushuptodolist.domain.user.entity.User
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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
    val user: User,

    @OneToMany(mappedBy = "post",cascade = [(CascadeType.ALL)], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf(),



){
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var postId: Long? = null


    @Column(name="likes")
    var likes: Long =0

    @Column(name ="created_at")
    var createdAt: LocalDateTime? = null

    @Column(name ="updated_at")
    var updatedAt: LocalDateTime? = null

    @PrePersist
    fun prePersist(){
        createdAt = LocalDateTime.now()
        updatedAt = null
    }


}

fun Post.toResponse(): PostResponse =

    PostResponse(
        postId = postId!!,
        postUserName = postUserName,
        title = title,
        description = description,
        createdAt =  createdAt!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        updatedAt = updatedAt?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        likes = likes
    )

