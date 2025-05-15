package org.example.tackit.domain.QnA_board.QnA_post.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_post.dto.request.QnAPostRequestDto;
import org.example.tackit.domain.QnA_board.QnA_post.dto.request.UpdateQnARequestDto;
import org.example.tackit.domain.QnA_board.QnA_post.dto.response.QnAPostResponseDto;
import org.example.tackit.domain.QnA_board.QnA_post.service.QnAPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna_post")
public class QnAPostController {
    private final QnAPostService qnAPostService;

    // 게시글 생성
    @PostMapping("/create")
    public ResponseEntity<QnAPostResponseDto> createQnAPost(@RequestBody QnAPostRequestDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername(); // userDetails로부터 이메일 획득
        QnAPostResponseDto response = qnAPostService.createPost(dto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 수정
    @PutMapping("/{QnAPostId}")
    public ResponseEntity<QnAPostResponseDto> updateQnAPost(@PathVariable("QnAPostId") long QnAPostId, @RequestBody UpdateQnARequestDto request, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        QnAPostResponseDto updateResponse = qnAPostService.update(QnAPostId, request, email);

        return ResponseEntity.ok().body(updateResponse);
    }

    // 게시글 삭제
    @DeleteMapping("/{QnAPostId}")
    public ResponseEntity<QnAPostResponseDto> deleteQnAPost(@PathVariable("QnAPostId") long QnAPostId, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        qnAPostService.delete(QnAPostId, email);

        return ResponseEntity.noContent().build();
    }

    // 게시글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<QnAPostResponseDto>> findALlQnAPost(){
        List<QnAPostResponseDto> posts = qnAPostService.findALl();
        return ResponseEntity.ok().body(posts);
    }

    // 게시글 상세 조회
    @GetMapping("/{QnAPostId}")
    public ResponseEntity<QnAPostResponseDto> findQnAPost(@PathVariable("QnAPostId") long QnAPostId) {
        QnAPostResponseDto post = qnAPostService.getPostById(QnAPostId);
        return ResponseEntity.ok(post);
    }

    // 게시글 신고
    @PostMapping("{QnAPostId}/report")
    public ResponseEntity<String> reportPost(@PathVariable long QnAPostId) {
        qnAPostService.increasePostReportCount(QnAPostId);
        return ResponseEntity.ok("게시글을 신고하였습니다.");
    }
}
