package org.example.tackit.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.FreePostDTO;
import org.example.tackit.domain.admin.dto.MemberDTO;
import org.example.tackit.domain.admin.service.AdminFreePostService;
import org.example.tackit.domain.admin.service.AdminMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminMemberService adminMemberService;
    private final AdminFreePostService adminFreePostService;
    // 1. 유저 조회
    @GetMapping("/member")
    public ResponseEntity<List<MemberDTO>> getAllUsers() {
        List<MemberDTO> users = adminMemberService.getAllMembersOrderByStatus();
        return ResponseEntity.ok(users);
    }

    // 2. 신고된 자유 게시글 조회
    @GetMapping("/free_post")
    public ResponseEntity<List<FreePostDTO>> getDeactivatedFreePosts() {
        return ResponseEntity.ok(adminFreePostService.getDeletedPosts());
    }

    // 3. 신고된 자유 게시글 Hard_Delete
    @DeleteMapping("/free_post/{postId}")
    public ResponseEntity<String> deleteReportedPost(@PathVariable("postId") Long id) {
        adminFreePostService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // 4. 비활성화 게시글 활성 전환
    @PatchMapping("/free_post/{postId}/activate")
    public ResponseEntity<String> activateReportedPost(@PathVariable("postId") Long id) {
        adminFreePostService.activatePost(id);
        return ResponseEntity.ok("게시글이 활성화되었습니다.");
    }

}
