package org.example.tackit.domain.QnA_board.QnA_tag.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_tag.dto.response.QnATagResponseDto;
import org.example.tackit.domain.QnA_board.QnA_tag.service.QnATagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna_tags")
public class QnATagController {
    private final QnATagService qnATagService;

    @GetMapping("/list")
    public ResponseEntity<List<QnATagResponseDto>> getAllTags(){
        return ResponseEntity.ok(qnATagService.getAllTags());
    }
}
