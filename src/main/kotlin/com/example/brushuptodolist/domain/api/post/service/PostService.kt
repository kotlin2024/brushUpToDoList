package com.example.brushuptodolist.domain.api.post.service

import com.example.brushuptodolist.domain.api.post.dto.LikeResponse
import com.example.brushuptodolist.domain.api.post.dto.PostPageResponse
import com.example.brushuptodolist.domain.api.post.dto.PostResponse
import com.example.brushuptodolist.domain.api.post.dto.UpdatePostRequest
import com.example.brushuptodolist.domain.api.post.entity.Like
import com.example.brushuptodolist.domain.api.post.entity.Post
import com.example.brushuptodolist.domain.api.post.entity.toResponse
import com.example.brushuptodolist.domain.api.post.repository.LikeRepository
import com.example.brushuptodolist.domain.api.post.repository.PostRepository
import com.example.brushuptodolist.domain.common.GetCurrentUser
import com.example.brushuptodolist.domain.user.repository.UserRepository
import com.example.brushuptodolist.infra.aop.ValidationPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val getCurrentUser: GetCurrentUser,
    private val likeRepository: LikeRepository,
) {

    fun readAllPost(pageable: Pageable):List<PostResponse> {
        val postList = postRepository.findAllByOrderByCreatedAtDesc(pageable)
        return postList.map{it.toResponse()}
    }

    fun readPostById(postId: Long): PostResponse =
        postRepository.findByPostId(postId)!!.toResponse()


    @ValidationPost
    @Transactional
    fun createPost(createPostRequest: UpdatePostRequest): PostResponse{


        val user= getCurrentUser.getCurrentUser()
        return postRepository.save(
            Post(
                title = createPostRequest.title,
                description =  createPostRequest.description,
                postUserName = user!!.userName,
                user = user,
            )
        ).toResponse()
    }

    @ValidationPost
    @Transactional
    fun updatePost(postId: Long, updatePostRequest: UpdatePostRequest): PostResponse {


        val user= getCurrentUser.getCurrentUser()

        val post = postRepository.findByPostId(postId) ?: throw RuntimeException("Post가 존재하지 않음")

        if (user != post.user) throw RuntimeException("당신이 작성한 게시글이 아님")

        post.title = updatePostRequest.title
        post.description = updatePostRequest.description
        post.updatedAt= LocalDateTime.now()

        return postRepository.save(post).toResponse()

    }

    @Transactional
    fun deletePost(postId: Long){

        val user= getCurrentUser.getCurrentUser()

        val post = postRepository.findByPostId(postId) ?: throw RuntimeException("Post가 존재하지 않음")

        if(user!=post.user) throw RuntimeException("당신이 작성한 게시글이 아님")

        postRepository.delete(post)

    }

    fun <T, R> Page<T>.toPostPageResponse(transform: (T) -> R): PostPageResponse<R> {
        return PostPageResponse(
            content = this.content.map(transform),
            pageNumber = this.number,
            pageSize = this.size,
            totalElements = this.totalElements,
            totalPages = this.totalPages,
            isLast = this.isLast
        )
    }

    @Transactional
    fun likePost(postId: Long): LikeResponse {

        val user= getCurrentUser.getCurrentUser() ?: throw RuntimeException("해당 유저는 유효하지 않음")

        val post = postRepository.findByPostId(postId)?: throw RuntimeException("해당 post가 존재하지 않음")



        try{
            val checkingLike = likeRepository.findByUserAndPost(user,post)

            if(!checkingLike.likesOrNot){
                post.likes+=1
                postRepository.save(post)

                checkingLike.likesOrNot = true
                return likeRepository.save(checkingLike).toResponse()
            }
            else{
                post.likes -=1
                postRepository.save(post)

                checkingLike.likesOrNot = false
                return likeRepository.save(checkingLike).toResponse()
            }

        } catch(e: Exception){
            likeRepository.save(
                Like(
                    user = user,
                    userName = user.userName,
                    post = post,
                    postTitle = post.title,
                )
            )
            val checkingLike = likeRepository.findByUserAndPost(user,post)

            post.likes+=1
            postRepository.save(post)
            checkingLike.likesOrNot = true
             return likeRepository.save(checkingLike).toResponse()
        }



    }


}