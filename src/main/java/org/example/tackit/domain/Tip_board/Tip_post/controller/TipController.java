package org.example.tackit.domain.Tip_board.Tip_post.controller;

import org.example.tackit.domain.Tip_board.Tip_post.dto.response.TipPostRespDto;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.Tip_board.Tip_post.dto.request.TipPostReqDto;
import org.example.tackit.domain.Tip_board.Tip_post.dto.request.TipPostUpdateDto;
import org.example.tackit.domain.Tip_board.Tip_post.dto.response.TipPostDto;
import org.example.tackit.domain.Tip_board.Tip_post.service.TipPostService;
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/tip-posts")
public class TipController {
    private final TipPostService tipService;

    public TipController(final TipPostService tipService) {
        this.tipService = tipService;
    }

    // 1. 게시글 전체 조회
    @GetMapping
    public ResponseEntity<PageResponseDTO<TipPostRespDto>> getAllPosts(
            @AuthenticationPrincipal CustomUserDetails user,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        String org = user.getOrganization();
        PageResponseDTO<TipPostRespDto> pageResponse = tipService.getActivePostsByOrganization(org, pageable);
        return ResponseEntity.ok(pageResponse);
    }

    // 2. 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<TipPostRespDto> getPostById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        String org = user.getOrganization();
        TipPostRespDto post = tipService.getPostById(id, org);
        return ResponseEntity.ok(post);
    }

    // 3. 게시글 작성
    @PostMapping
    public ResponseEntity<TipPostRespDto> create(
            @RequestPart("dto") TipPostReqDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal CustomUserDetails user) throws IOException {
        String email = user.getEmail();
        String org = user.getOrganization();
        TipPostRespDto post = tipService.createPost(dto, email, org, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // 4. 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<TipPostRespDto> update(
            @PathVariable Long id,
            @RequestPart("dto") TipPostUpdateDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal CustomUserDetails user) throws IOException {

        String email = user.getEmail();
        String org = user.getOrganization();

        TipPostRespDto updatedPost = tipService.update(id, dto, email, org, image);
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
    public ResponseEntity<String> scrapPost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {

        String message = tipService.toggleScrap(id, user.getId());
        return ResponseEntity.ok(message);
    }

    // 7. 게시글 신고
    @PostMapping("{id}/report")
    public ResponseEntity<String> reportPost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        String org = user.getOrganization();

        String message = tipService.report(id, user.getId());
        return ResponseEntity.ok(message);
    }
}
