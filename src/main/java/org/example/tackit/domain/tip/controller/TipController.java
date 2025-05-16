package org.example.tackit.domain.tip.controller;

import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.mypage.service.MemberService;
import org.example.tackit.domain.tip.dto.request.TipPostCreateDTO;
import org.example.tackit.domain.tip.dto.request.TipPostUpdateDTO;
import org.example.tackit.domain.tip.dto.response.TipPostDTO;
import org.example.tackit.domain.tip.service.TipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tip-posts")
public class TipController {
    private final TipService tipService;

    public TipController(final TipService tipService, MemberService memberService) {
        this.tipService = tipService;
    }

    // 1. 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<TipPostDTO>> getAllPosts(@AuthenticationPrincipal CustomUserDetails user) {
        String org = user.getOrganization();
        List<TipPostDTO> posts = tipService.getActivePostsByOrganization(org);
        return ResponseEntity.ok(posts);
    }

    // 2. 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<TipPostDTO> getPostById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        String org = user.getOrganization();
        TipPostDTO post = tipService.getPostById(id, org);
        return ResponseEntity.ok(post);
    }

    // 3. 게시글 작성
    @PostMapping
    public ResponseEntity<TipPostDTO> create(
            @RequestBody TipPostCreateDTO dto,
            @AuthenticationPrincipal CustomUserDetails user) {
        TipPostDTO post = tipService.createPost(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // 4. 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<TipPostDTO> update(
            @PathVariable Long id,
            @RequestBody TipPostUpdateDTO dto,
            @AuthenticationPrincipal CustomUserDetails user) {
        TipPostDTO updatedPost = tipService.updatePost(id, dto, user);
        return ResponseEntity.ok(updatedPost);
    }

    // 5. 게시글 삭제
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        tipService.deletePost(id, user);
        return ResponseEntity.noContent().build();
    }

    // 6. 게시글 스크랩
    @PostMapping("/{id}/scrap")
    public ResponseEntity<Void> scrapPost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        tipService.scrapPost(id, user.getId());
        return ResponseEntity.ok().build();
    }
}
