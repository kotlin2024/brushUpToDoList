package com.example.brushuptodolist.domain.api.comment.controller

import com.example.brushuptodolist.domain.api.comment.dto.CommentResponse
import com.example.brushuptodolist.domain.api.comment.dto.UpdateCommentRequest
import com.example.brushuptodolist.domain.api.comment.service.CommentService
import com.example.brushuptodolist.domain.api.post.dto.PostPageResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/comment")
@RestController
class CommentController(
    private val commentService: CommentService
) {


    @Operation(summary = "페이지네이션 적용", responses = [
        ApiResponse(description = "Successful Operation", responseCode = "200",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = PostPageResponse::class))])
    ])
    @GetMapping("/{postId}")
    fun readComment(
        @PathVariable postId:Long,
        @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<List<CommentResponse>> =
        ResponseEntity.status(HttpStatus.OK).body(commentService.readComment(postId, pageable))

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