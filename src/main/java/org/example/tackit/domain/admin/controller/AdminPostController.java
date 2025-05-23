package org.example.tackit.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.ReportedPostDTO;
import org.example.tackit.domain.admin.service.ReportedPostService;
import org.example.tackit.domain.entity.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/report")
@RequiredArgsConstructor
public class AdminPostController {
    private final Map<Post, ReportedPostService> reportedPostServices;

    // 신고된 게시글 조회
    @GetMapping("/{postType}/posts")
    public ResponseEntity<List<ReportedPostDTO>> getReportedPosts(@PathVariable("postType") Post postType) {
        ReportedPostService service = reportedPostServices.get(postType);

        if (service == null) {
            throw new IllegalArgumentException("지원하지 않는 게시글 유형입니다: " + postType);
        }

        List<ReportedPostDTO> reportedPosts = service.getDeletedPosts();
        return ResponseEntity.ok(reportedPosts);
    }

    // 게시글 완전 삭제
    @DeleteMapping("/{postType}/posts/{postId}")
    public ResponseEntity<Void> deleteReportedPost(
            @PathVariable("postType") Post postType,
            @PathVariable("postId") Long postId) {

        ReportedPostService service = reportedPostServices.get(postType);
        if (service == null) {
            throw new IllegalArgumentException("지원하지 않는 게시글 유형입니다: " + postType);
        }

        service.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    // 게시글 활성화
    @PatchMapping("/{postType}/posts/{postId}/activate")
    public ResponseEntity<String> activateReportedPost(
            @PathVariable("postType") Post postType,
            @PathVariable("postId") Long postId) {

        ReportedPostService service = reportedPostServices.get(postType);
        if (service == null) {
            throw new IllegalArgumentException("지원하지 않는 게시글 유형입니다: " + postType);
        }

        service.activatePost(postId);
        return ResponseEntity.ok("게시글이 활성화되었습니다.");
    }

}
