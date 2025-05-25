package org.example.tackit.domain.QnA_board.QnA_tag.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_tag.dto.response.QnATagPostResponseDto;
import org.example.tackit.domain.QnA_board.QnA_tag.dto.response.QnATagResponseDto;
import org.example.tackit.domain.QnA_board.QnA_tag.service.QnATagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qna-tags")
public class QnATagController {
    private final QnATagService qnATagService;

    // 전체 태그 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<QnATagResponseDto>> getAllTags(){
        return ResponseEntity.ok(qnATagService.getAllTags());
    }

    // 특정 태그가 포함된 게시글 리스트 조회
    @GetMapping("/{tagId}/posts")
    public ResponseEntity<List<QnATagPostResponseDto>> getPostsByTag(@PathVariable Long tagId) {
        List<QnATagPostResponseDto> posts = qnATagService.getPostsByTag(tagId);
        return ResponseEntity.ok(posts);
    }
}
