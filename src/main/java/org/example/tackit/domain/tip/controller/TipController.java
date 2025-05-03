package org.example.tackit.domain.tip.controller;

import org.example.tackit.domain.tip.dto.response.TipPostDTO;
import org.example.tackit.domain.tip.service.TipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tip")
public class TipController {
    private final TipService tipService;
    public TipController(final TipService tipService) {
        this.tipService = tipService;
    }

    // 1. 게시글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<TipPostDTO>> getAllPosts() {
        List<TipPostDTO> posts = tipService.getAllActivePosts();
        return ResponseEntity.ok(posts);
    }

}
