package org.example.tackit.domain.Free_board.Free_tag.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_tag.dto.response.FreeTagPostResponseDto;
import org.example.tackit.domain.Free_board.Free_tag.dto.response.FreeTagResponseDto;
import org.example.tackit.domain.Free_board.Free_tag.service.FreeTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/free_tags")
public class FreeTagController {
    private final FreeTagService freeTagService;

    // 전체 태그 목록 조회
    @GetMapping
    public ResponseEntity<List<FreeTagResponseDto>> getAllTags(){
        return ResponseEntity.ok(freeTagService.getAllTags());
    }

    // 특정 태그가 포함된 게시글 리스트 조회
    @GetMapping("/{tagId}/posts")
    public ResponseEntity<List<FreeTagPostResponseDto>> getPostsByTag(@PathVariable Long tagId) {
        List<FreeTagPostResponseDto> posts = freeTagService.getPostsByTag(tagId);
        return ResponseEntity.ok(posts);
    }
}
