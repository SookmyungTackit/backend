package org.example.tackit.domain.free_post.controller;

import org.example.tackit.domain.free_post.dto.request.FreePostCreateDTO;
import org.example.tackit.domain.free_post.dto.request.FreePostUpdateDTO;
import org.example.tackit.domain.free_post.dto.response.FreePostDTO;
import org.example.tackit.domain.free_post.service.FreePostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/free_post")
public class FreePostController {
    private final FreePostService freePostService;

    public FreePostController(FreePostService freePostService) {
        this.freePostService = freePostService;
    }

    // 1. 게시글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<FreePostDTO>> getAllPosts() {
        List<FreePostDTO> posts = freePostService.getAllActivePosts();
        return ResponseEntity.ok(posts);
    }


    // 2. 게시글 상세 조회
    @GetMapping("/{freePostId}")
    public ResponseEntity<FreePostDTO> getPostById(@PathVariable("freePostId") Long freePostId) {
        FreePostDTO post = freePostService.getPostById(freePostId);
        return ResponseEntity.ok(post);
    }

    // 3. 게시글 작성
    @PostMapping("/create")
    public ResponseEntity<FreePostDTO> createPost(@RequestBody FreePostCreateDTO dto) {
        FreePostDTO post = freePostService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // 4. 게시글 수정
    @PutMapping("/{freePostId}")
    public ResponseEntity<FreePostDTO> updatePost(
            @PathVariable Long freePostId,
            @RequestBody FreePostUpdateDTO dto) {
        FreePostDTO updatedPost = freePostService.updatePost(freePostId, dto);
        return ResponseEntity.ok(updatedPost);
    }

    // 5. 게시글 삭제
    @DeleteMapping("{freePostId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long freePostId) {
        freePostService.deletePost(freePostId);
        return ResponseEntity.noContent().build();
    }

}
