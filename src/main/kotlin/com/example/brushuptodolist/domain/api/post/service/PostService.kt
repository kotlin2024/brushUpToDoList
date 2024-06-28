package com.example.brushuptodolist.domain.api.post.service

import com.example.brushuptodolist.domain.api.post.dto.PostResponse
import com.example.brushuptodolist.domain.api.post.dto.UpdatePostRequest
import com.example.brushuptodolist.domain.api.post.entity.Post
import com.example.brushuptodolist.domain.api.post.entity.toResponse
import com.example.brushuptodolist.domain.api.post.repository.PostRepository
import com.example.brushuptodolist.domain.common.GetCurrentUser
import com.example.brushuptodolist.domain.user.repository.UserRepository
import com.example.brushuptodolist.infra.aop.ValidationPost
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val getCurrentUser: GetCurrentUser,
) {


    fun readAllPost():List<PostResponse> {
        val postList = postRepository.findAll()
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

        return postRepository.save(post).toResponse()

    }

    @Transactional
    fun deletePost(postId: Long){

        val user= getCurrentUser.getCurrentUser()

        val post = postRepository.findByPostId(postId) ?: throw RuntimeException("Post가 존재하지 않음")

        if(user!=post.user) throw RuntimeException("당신이 작성한 게시글이 아님")

        postRepository.delete(post)

    }
}