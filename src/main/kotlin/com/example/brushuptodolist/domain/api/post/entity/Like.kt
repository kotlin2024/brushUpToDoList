package com.example.brushuptodolist.domain.api.post.entity

import com.example.brushuptodolist.domain.api.post.dto.LikeResponse
import com.example.brushuptodolist.domain.user.entity.User
import jakarta.persistence.*

@Entity
@Table(name = "likes")
class Like(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post,


    @Column(name="user_name")
    var userName: String,

    @Column(name="post_title")
    var postTitle: String,

    @Column(name = "count_like")
    var likesOrNot: Boolean = false

) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Like.toResponse(): LikeResponse =
    LikeResponse(
        postId = post.postId!!,
        postTitle = postTitle,
        userId= user.userId!!,
        userName= userName,
        likesOrNot = likesOrNot
)
