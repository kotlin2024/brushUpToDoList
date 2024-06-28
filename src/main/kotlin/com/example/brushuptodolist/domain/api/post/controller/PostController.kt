package com.example.brushuptodolist.domain.api.post.controller

import com.example.brushuptodolist.domain.api.post.dto.LikeResponse
import com.example.brushuptodolist.domain.api.post.dto.PostPageResponse
import com.example.brushuptodolist.domain.api.post.dto.PostResponse
import com.example.brushuptodolist.domain.api.post.dto.UpdatePostRequest
import com.example.brushuptodolist.domain.api.post.entity.Post
import com.example.brushuptodolist.domain.api.post.service.PostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.apache.coyote.Response
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*


@RequestMapping("/api")
@RestController
class PostController(
    private val postService: PostService,
) {


    @Operation(summary = "페이지네이션 적용", responses = [
        ApiResponse(description = "Successful Operation", responseCode = "200",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = PostPageResponse::class))])
    ])
    @GetMapping
    fun readAllPost(@PageableDefault(size = 10) pageable: Pageable): ResponseEntity<List<PostResponse>> =
        ResponseEntity.status(HttpStatus.OK).body(postService.readAllPost(pageable))


    @GetMapping("/{postId}")
    fun readPostById(@PathVariable postId: Long): ResponseEntity<PostResponse> =
        ResponseEntity.status(HttpStatus.OK).body(postService.readPostById(postId))


    @PostMapping
    fun createPost(@RequestBody createPostRequest: UpdatePostRequest): ResponseEntity<PostResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createPostRequest))


    @PutMapping("/{postId}")
    fun updatePost(@PathVariable postId: Long, @RequestBody updatePostRequest: UpdatePostRequest): ResponseEntity<PostResponse> =
        ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(postId, updatePostRequest ))


    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(postService.deletePost(postId))


    @PutMapping("/{postId}/like")
    fun likePost(@PathVariable postId: Long): ResponseEntity<LikeResponse> =
        ResponseEntity.status(HttpStatus.OK).body(postService.likePost(postId))
}