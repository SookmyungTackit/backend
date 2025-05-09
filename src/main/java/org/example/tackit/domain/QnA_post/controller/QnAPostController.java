package org.example.tackit.domain.QnA_post.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_post.dto.request.QnAPostRequestDto;
import org.example.tackit.domain.QnA_post.dto.request.UpdateQnARequestDto;
import org.example.tackit.domain.QnA_post.dto.response.QnAPostResponseDto;
import org.example.tackit.domain.QnA_post.repository.QnAPostRepository;
import org.example.tackit.domain.QnA_post.service.QnAPostService;
import org.example.tackit.domain.entity.QnAPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna_post")
public class QnAPostController {
    private final QnAPostService qnAPostService;

    // 게시글 생성
    @PostMapping("/create")
    public ResponseEntity<QnAPostResponseDto> createQnAPost(@RequestBody QnAPostRequestDto dto) {
        QnAPostResponseDto response = qnAPostService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 수정
    @PutMapping("/{QnAPostId}")
    public ResponseEntity<QnAPostResponseDto> updateQnAPost(@PathVariable("QnAPostId") Long QnAPostId, @RequestBody UpdateQnARequestDto request){
        QnAPostResponseDto updateResponse = qnAPostService.update(QnAPostId, request);

        return ResponseEntity.ok().body(updateResponse);
    }

    // 게시글 삭제
    @DeleteMapping("/{QnAPostId}")
    public ResponseEntity<QnAPostResponseDto> deleteQnAPost(@PathVariable("QnAPostId") Long QnAPostId){
        qnAPostService.delete(QnAPostId);

        return ResponseEntity.noContent().build();
    }

    // 게시글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<QnAPostResponseDto>> findALlQnAPost(){
        List<QnAPostResponseDto> posts = qnAPostService.findALl();
        return ResponseEntity.ok().body(posts);
    }
}
