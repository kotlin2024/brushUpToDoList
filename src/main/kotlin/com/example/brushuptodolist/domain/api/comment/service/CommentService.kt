package com.example.brushuptodolist.domain.api.comment.service

import com.example.brushuptodolist.domain.api.comment.dto.CommentResponse
import com.example.brushuptodolist.domain.api.comment.dto.UpdateCommentRequest
import com.example.brushuptodolist.domain.api.comment.entity.Comment
import com.example.brushuptodolist.domain.api.comment.entity.toResponse
import com.example.brushuptodolist.domain.api.comment.repository.CommentRepository
import com.example.brushuptodolist.domain.api.post.repository.PostRepository
import com.example.brushuptodolist.domain.common.GetCurrentUser
import com.example.brushuptodolist.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CommentService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val getCurrentUser: GetCurrentUser,
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

        val user= getCurrentUser.getCurrentUser() ?:throw RuntimeException(" 인증되지 않은 유저")

        val commentUser = commentRepository.findByCommentId(commentId = commentId) ?: throw RuntimeException("dafs")

        if(user != commentUser.user) throw RuntimeException("해당 post에 당신이 작성한 댓글이 아닙니다")

        commentUser.commentDescription = updateCommentRequest.commentDescription
        commentUser.commentUpdatedAt = LocalDateTime.now()

        return commentRepository.save(commentUser).toResponse()

    }

    @Transactional
    fun createComment(
        postId: Long,
        updateCommentRequest: UpdateCommentRequest
    ): CommentResponse{

        val post = postRepository.findByPostId(postId) ?: throw RuntimeException("존재하지 않는 post 입니다")

        val user = getCurrentUser.getCurrentUser()

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
        commentId: Long
    ){

        val post = postRepository.findByPostId(postId) ?: throw RuntimeException("존재하지 않는 post 입니다")

        val user= getCurrentUser.getCurrentUser() ?:throw RuntimeException(" 인증되지 않은 유저")

        val commentUser = commentRepository.findByCommentId(commentId)?: throw RuntimeException("해당 댓글이 존재하지 않습니다")

        if(user != commentUser.user) throw RuntimeException("당신이 작성한 댓글이 아닙니다.")

        commentRepository.delete(commentUser)

    }
}