package com.example.brushuptodolist.domain.api.post.controller

import com.example.brushuptodolist.domain.api.post.dto.PostResponse
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
        ResponseEntity.status(HttpStatus.OK).body(postService.readAllPost()) // 지금 리스트 몽땅 가져왔는데 map안해도 될려나


    @GetMapping("/{postId}")
    fun readPostById(@PathVariable postId: Long): ResponseEntity<PostResponse> =
        ResponseEntity.status(HttpStatus.OK).body(postService.readPostById(postId))


    @PostMapping
    fun createPost(): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost())


    @PutMapping("/{postId}")
    fun updatePost(@PathVariable postId: Long): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(postId))


    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(postService.deletePost(postId))

}