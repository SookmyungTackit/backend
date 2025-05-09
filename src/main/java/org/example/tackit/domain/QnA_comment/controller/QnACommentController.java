package org.example.tackit.domain.QnA_comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_comment.dto.request.QnACommentCreateDto;
import org.example.tackit.domain.QnA_comment.dto.request.QnACommentUpdateDto;
import org.example.tackit.domain.QnA_comment.dto.response.QnACommentResponseDto;
import org.example.tackit.domain.QnA_comment.service.QnACommentService;
import org.example.tackit.domain.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna_comment")
public class QnACommentController {

    private final QnACommentService qnACommentService;

    // 댓글 작성
    @PostMapping("/create")
    public ResponseEntity<QnACommentResponseDto> createComment(@RequestBody QnACommentCreateDto request, @AuthenticationPrincipal Member member) {
        QnACommentResponseDto response = qnACommentService.createComment(request, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 댓글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<QnACommentResponseDto>> getComments(@PathVariable long postId){
        return ResponseEntity.ok(qnACommentService.getCommentByPost(postId));
    }

    // 댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<QnACommentResponseDto> updateComment(@PathVariable long commentId, @RequestBody QnACommentUpdateDto request, @AuthenticationPrincipal Member member){
        QnACommentResponseDto updateResponse = qnACommentService.updateComment(commentId, request, member);
        return ResponseEntity.ok().body(updateResponse);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable long commentId, @AuthenticationPrincipal Member member){
        qnACommentService.deleteComment(commentId, member);
        return ResponseEntity.noContent().build();
    }

}
