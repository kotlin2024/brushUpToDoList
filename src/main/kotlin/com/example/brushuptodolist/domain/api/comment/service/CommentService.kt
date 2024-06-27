package com.example.brushuptodolist.domain.api.comment.service

import com.example.brushuptodolist.domain.api.comment.dto.CommentResponse
import com.example.brushuptodolist.domain.api.comment.dto.UpdateCommentRequest
import com.example.brushuptodolist.domain.api.comment.entity.Comment
import com.example.brushuptodolist.domain.api.comment.entity.toResponse
import com.example.brushuptodolist.domain.api.comment.repository.CommentRepository
import com.example.brushuptodolist.domain.api.post.repository.PostRepository
import com.example.brushuptodolist.domain.authentication.jwt.UserPrincipal
import com.example.brushuptodolist.domain.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
) {

    fun readComment(postId:Long): List<CommentResponse>{

        val post = postRepository.findByPostId(postId) ?: throw RuntimeException("존재하지 않는 post입니다")

        return commentRepository.findAllByPost(post).map{it.toResponse()}
    }

    @Transactional
    fun updateComment(
        postId: Long,
        commentId: Long,
        updateCommentRequest: UpdateCommentRequest
    ): CommentResponse {

        val post = postRepository.findByPostId(postId) ?: throw RuntimeException("존재하지 않는 post 입니다")

        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        val user = userRepository.findByUserEmail(userPrincipal.userEmail)

        val commentUser = commentRepository.findByPostAndCommentId(post, commentId)

        if(user != commentUser.user) throw RuntimeException("해당 post에 당신이 작성한 댓글이 아닙니다")

        commentUser.commentDescription = updateCommentRequest.commentDescription

        return commentRepository.save(commentUser).toResponse()

    }

    @Transactional
    fun createComment(
        postId: Long,
        updateCommentRequest: UpdateCommentRequest
    ): CommentResponse{
        val post = postRepository.findByPostId(postId) ?: throw RuntimeException("존재하지 않는 post 입니다") //TODO() 해당 예외처리와 더불어 NULL CHECKING 해야함

        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        val user = userRepository.findByUserEmail(userPrincipal.userEmail)

        return commentRepository.save(
            Comment(
                commenterName = user!!.userName,
                commentDescription = updateCommentRequest.commentDescription,
                user = user,
                post = post,
            )
        ).toResponse()

    }

    @Transactional
    fun deleteComment(
        postId: Long,
        commentId: Long){

        val post = postRepository.findByPostId(postId) ?: throw RuntimeException("존재하지 않는 post 입니다") //TODO() 해당 예외처리와 더불어 NULL CHECKING 해야함

        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        val user = userRepository.findByUserEmail(userPrincipal.userEmail)

        val commentUser = commentRepository.findByPostAndCommentId(post, commentId)

        commentRepository.delete(commentUser)

    }
    //TODO() post, userPrincipal,user 지금 계속 반복되니 하나의 class로 만들어서 코드 간단하게 만드는거 해보기
}