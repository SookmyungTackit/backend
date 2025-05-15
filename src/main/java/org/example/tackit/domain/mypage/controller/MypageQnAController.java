package org.example.tackit.domain.mypage.controller;


import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.mypage.dto.response.QnAMyCommentResponseDto;
import org.example.tackit.domain.mypage.dto.response.QnAMyPostResponseDto;
import org.example.tackit.domain.mypage.service.MyPageQnAService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageQnAController {

    private final MyPageQnAService myPageQnAService;

    // 질문게시판) 내가 쓴 게시글 조회
    @GetMapping("/qna_posts")
    public ResponseEntity<List<QnAMyPostResponseDto>> getMyQnaPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(myPageQnAService.getMyPosts(userDetails.getUsername()));
    }

    // 질문게시판) 내가 쓴 댓글 조회
    @GetMapping("/qna_comments")
    public ResponseEntity<List<QnAMyCommentResponseDto>> getMyQnaComments(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(myPageQnAService.getMyComments(userDetails.getUsername()));
    }

}
