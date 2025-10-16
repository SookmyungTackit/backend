package org.example.tackit.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.MemberStatisticsDTO;
import org.example.tackit.domain.admin.service.AdminDashboardService;
import org.example.tackit.domain.admin.service.AdminMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {
    private final AdminMemberService adminMemberService;
    private final AdminDashboardService adminDashboardService;

    // [ DAU ]
    @GetMapping("/users/dau")
    public ResponseEntity<Long> getDauCount() {
        Long dau = adminDashboardService.getDau(LocalDate.now());
        return ResponseEntity.ok(dau);
    }

    // [ MAU ]
    @GetMapping("/users/mau")
    public ResponseEntity<Long> getMauCount() {
        Long mau = adminDashboardService.getMau(LocalDate.now());
        return ResponseEntity.ok(mau);
    }

    // [ 총 게시글 수 ]
    @GetMapping("/posts/count")
    public ResponseEntity<Long> getPostsCount() {
        long count = adminDashboardService.getPostsCount();
        return ResponseEntity.ok(count);
    }

    // [ 신고로 비활성화된 게시글 수 ]
    @GetMapping("/deleted-posts/count")
    public ResponseEntity<Long> getDeletedPostsCount() {
        long count = adminDashboardService.getDeletedPostsByReport();
        return ResponseEntity.ok(count);
    }

    // [ 신고로 비활성화된 게시글 정보 ]
    // 유형 : 댓글/게시글
    // 제목 :
    // 신고 사유 :
    // 상태 : 신고횟수 / 비활성(3회 이상)
    // 신고일자
    // URL

    // [ 회원 가입 통계 ]
    @GetMapping("/user-statistics")
    public ResponseEntity<MemberStatisticsDTO> getMemberStatistics() {
        MemberStatisticsDTO stats = adminMemberService.getMemberStatistics();
        return ResponseEntity.ok(stats);
    }



}
