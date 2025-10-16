package org.example.tackit.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.ReportedPostDTO;
import org.example.tackit.domain.admin.service.ReportedPostService;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/report")
@RequiredArgsConstructor
public class AdminPostController {
    private final Map<Post, ReportedPostService> reportedPostServices;

    // - 총 게시글 수
    //- DAU(하루 동안 실제로 서비스에 접속해 활동한 순 사용자 수)
    //- MAU(한 달 동안 한 번이라도 서비스를 이용한 순 사용자 수)
    //- DAU/MAU
    //- 월별 가입자 수, 월별 활성 사용자 수(MAU)
    //- 탈퇴한 계정 수
    //- 신고 3회 누적으로 삭제된 게시글 혹은 댓글 수

    // 신고된 게시글 조회
    @GetMapping("/{postType}/posts")
    public ResponseEntity<PageResponseDTO<ReportedPostDTO>> getReportedPosts(
            @PathVariable("postType") Post postType,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        ReportedPostService service = reportedPostServices.get(postType);

        if (service == null) {
            throw new IllegalArgumentException("지원하지 않는 게시글 유형입니다: " + postType);
        }

        Page<ReportedPostDTO> posts = service.getDeletedPosts(pageable);
        return ResponseEntity.ok(PageResponseDTO.from(posts));
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
