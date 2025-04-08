package org.example.tackit.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.FreePostDTO;
import org.example.tackit.domain.admin.dto.UserDTO;
import org.example.tackit.domain.admin.service.AdminFreePostService;
import org.example.tackit.domain.admin.service.AdminUserService;
import org.example.tackit.domain.entity.FreePost;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminUserService adminUserService;
    private final AdminFreePostService adminFreePostService;
    // 1. 유저 조회
    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = adminUserService.getAllUsersOrderByStatus();
        return ResponseEntity.ok(users);
    }

    // 2. 신고 자유 게시글 조회
    @GetMapping("/free_post")
    public ResponseEntity<List<FreePostDTO>> getReportedPosts() {
        return ResponseEntity.ok(adminFreePostService.getReportedPosts());
    }

    // 3. 신고 자유 게시글 비활성화
    @PatchMapping("/free_post/{postId}/deactivate")
    public ResponseEntity<String> deactivatePost(@PathVariable("postId") Long id) {
        adminFreePostService.deactivatedPosts(id);
        return ResponseEntity.ok("게시글이 비활성화되었습니다.");
    }

    // 4. 신고 자유 게시글 완전 삭제
    @DeleteMapping("/free_post/{postId}")
    public ResponseEntity<String> deleteReportedPost(@PathVariable("postId") Long id) {
        adminFreePostService.deletePost(id);
        return ResponseEntity.noContent().build();

    }
}
