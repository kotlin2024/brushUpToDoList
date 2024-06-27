package com.example.brushuptodolist.domain.api.comment.controller

import com.example.brushuptodolist.domain.api.comment.dto.CommentResponse
import com.example.brushuptodolist.domain.api.comment.dto.UpdateCommentRequest
import com.example.brushuptodolist.domain.api.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/comment")
@RestController
class CommentController(
    private val commentService: CommentService
) {

    @GetMapping("/{postId}")
    fun readComment(
        @PathVariable postId:Long
    ): ResponseEntity<List<CommentResponse>> =
        ResponseEntity.status(HttpStatus.OK).body(commentService.readComment(postId))

    @PostMapping("/{postId}")
    fun createComment(
        @PathVariable postId:Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(postId,updateCommentRequest))

    @PutMapping("/{postId}/{commentId}")
    fun updateComment(
        @PathVariable postId:Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> =
        ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(postId,commentId,updateCommentRequest))

    @DeleteMapping("/{postId}/{commentId}")
    fun deleteComment(
        @PathVariable postId:Long,
        @PathVariable commentId: Long
    ):ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.NO_CONTENT).body(commentService.deleteComment(postId, commentId))
}