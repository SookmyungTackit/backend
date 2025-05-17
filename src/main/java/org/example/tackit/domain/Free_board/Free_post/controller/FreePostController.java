package org.example.tackit.domain.Free_board.Free_post.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_post.dto.request.FreePostReqDto;
import org.example.tackit.domain.Free_board.Free_post.dto.request.UpdateFreeReqDto;
import org.example.tackit.domain.Free_board.Free_post.dto.response.FreePostRespDto;
import org.example.tackit.domain.Free_board.Free_post.service.FreePostService;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/free-posts")
@RequiredArgsConstructor
public class FreePostController {
    private final FreePostService freePostService;

    // 1. 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<FreePostRespDto>> findAllFreePosts(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        String org = user.getOrganization();
        List<FreePostRespDto> posts = freePostService.findAll(org);
        return ResponseEntity.ok(posts);
    }

    // 2. 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<FreePostRespDto> findFreePost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        String org = user.getOrganization();
        FreePostRespDto post = freePostService.getPostById(id, org);
        return ResponseEntity.ok(post);
    }

    // 3. 게시글 작성
    @PostMapping
    public ResponseEntity<FreePostRespDto> createFreePost(
            @RequestBody FreePostReqDto dto,
            @AuthenticationPrincipal CustomUserDetails user) {
        String email = user.getEmail();
        String org = user.getOrganization();
        FreePostRespDto resp = freePostService.createPost(dto, email, org);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // 4. 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<FreePostRespDto> updateFreePost(
            @PathVariable Long id,
            @RequestBody UpdateFreeReqDto req,
            @AuthenticationPrincipal CustomUserDetails user) {

        String email = user.getEmail();
        String org = user.getOrganization();
        FreePostRespDto updateResp = freePostService.update(id, req, email, org);

        return ResponseEntity.ok(updateResp);
    }

    // 5. 게시글 삭제
    @DeleteMapping("{id}")
    public ResponseEntity<FreePostRespDto> deleteFreePost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        String email = user.getEmail();
        String org = user.getOrganization();
        freePostService.delete(id, email, org);

        return ResponseEntity.noContent().build();
    }

    // 6. 게시글 신고
    @PostMapping("{id}/report")
    public ResponseEntity<String> reportPost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        String org = user.getOrganization();
        freePostService.increasePostReportCount(id);
        return ResponseEntity.ok("게시글을 신고하였습니다.");
    }

}
