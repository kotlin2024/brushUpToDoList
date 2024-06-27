package com.example.brushuptodolist.domain.api.post.controller

import com.example.brushuptodolist.domain.api.post.dto.PostResponse
import com.example.brushuptodolist.domain.api.post.dto.UpdatePostRequest
import com.example.brushuptodolist.domain.api.post.entity.Post
import com.example.brushuptodolist.domain.api.post.service.PostService
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*


@RequestMapping("/api")
@RestController
class PostController(
    private val postService: PostService,
) {

    @GetMapping
    fun readAllPost(): ResponseEntity<List<PostResponse>> =
        ResponseEntity.status(HttpStatus.OK).body(postService.readAllPost())


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
}