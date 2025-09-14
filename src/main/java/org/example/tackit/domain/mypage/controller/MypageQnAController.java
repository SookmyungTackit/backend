package org.example.tackit.domain.mypage.controller;


import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.mypage.dto.response.QnAMyCommentResponseDto;
import org.example.tackit.domain.mypage.dto.response.QnAMyPostResponseDto;
import org.example.tackit.domain.mypage.service.MyPageQnAService;
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageQnAController {

    private final MyPageQnAService myPageQnAService;

    // 질문게시판) 내가 쓴 게시글 조회
    @GetMapping("/qna-posts")
    public ResponseEntity<PageResponseDTO<QnAMyPostResponseDto>> getMyQnaPosts(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 5) Pageable pageable
    ) {
        return ResponseEntity.ok(
                myPageQnAService.getMyPosts(userDetails.getUsername(), pageable)
        );
    }

    // 질문게시판) 내가 쓴 댓글 조회
    @GetMapping("/qna-comments")
    public ResponseEntity<PageResponseDTO<QnAMyCommentResponseDto>> getMyQnaComments(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 5) Pageable pageable
    ) {
        return ResponseEntity.ok(
                myPageQnAService.getMyComments(userDetails.getUsername(), pageable)
        );
    }

}
