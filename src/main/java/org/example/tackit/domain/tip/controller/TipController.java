package org.example.tackit.domain.tip.controller;

import org.example.tackit.domain.auth.login.service.CustomUserDetailsService;
import org.example.tackit.domain.entity.Member;
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
@RequestMapping("/tip")
public class TipController {
    private final TipService tipService;
    private final MemberService memberService;

    public TipController(final TipService tipService, MemberService memberService) {
        this.tipService = tipService;
        this.memberService = memberService;
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

    // 6. 게시글 찜
    /*
    @PostMapping("/{tipPostId}/scrap")
    public ResponseEntity<?> scrapPost(@PathVariable Long tipPostId,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long memberId = userDetails.getMemberId();
        tipService.scrapPost(memberId, tipPostId);
        return ResponseEntity.ok("스크랩 완료");
    }

     */
}
