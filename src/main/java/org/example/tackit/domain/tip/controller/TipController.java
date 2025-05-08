package org.example.tackit.domain.tip.controller;

import org.example.tackit.domain.tip.dto.request.TipPostCreateDTO;
import org.example.tackit.domain.tip.dto.request.TipPostUpdateDTO;
import org.example.tackit.domain.tip.dto.response.TipPostDTO;
import org.example.tackit.domain.tip.service.TipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tip")
public class TipController {
    private final TipService tipService;
    public TipController(final TipService tipService) {
        this.tipService = tipService;
    }

    // 1. 게시글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<TipPostDTO>> getAllPosts() {
        List<TipPostDTO> posts = tipService.getAllActivePosts();
        return ResponseEntity.ok(posts);
    }

    // 2. 게시글 상세 조회
    @GetMapping("/{tipPostId}")
    public ResponseEntity<TipPostDTO> getPostById(@PathVariable("tipPostId") Long tipPostId) {
        TipPostDTO post = tipService.getPostById(tipPostId);
        return ResponseEntity.ok(post);
    }

    // 3. 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<TipPostDTO> writePost(@RequestBody TipPostCreateDTO dto) {
        TipPostDTO post = tipService.writePost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // 4. 게시글 수정
    @PutMapping("/{tipPostId}")
    public ResponseEntity<TipPostDTO> updatePost(
            @PathVariable("tipPostId") Long tipPostId,
            @RequestBody TipPostUpdateDTO dto) {
        TipPostDTO updatedPost = tipService.updatePost(tipPostId, dto);
        return ResponseEntity.ok(updatedPost);
    }

    // 5. 게시글 삭제
    @DeleteMapping("{tipPostId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long tipPostId) {
        tipService.deletePost(tipPostId);
        return ResponseEntity.noContent().build();
    }

}
