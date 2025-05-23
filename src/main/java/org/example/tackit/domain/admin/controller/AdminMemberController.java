package org.example.tackit.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.MemberDTO;
import org.example.tackit.domain.admin.dto.MemberStatisticsDTO;
import org.example.tackit.domain.admin.service.AdminMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    // 1. 전체 유저 조회
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllUsers() {
        List<MemberDTO> users = adminMemberService.getAllMembersOrderByStatus();
        return ResponseEntity.ok(users);
    }

    // 2. 전체 유저, 이번 달 가입 유저, 이번 주 가입 유저
    @GetMapping("/statistics")
    public ResponseEntity<MemberStatisticsDTO> getMemberStatistics() {
        MemberStatisticsDTO stats = adminMemberService.getMemberStatistics();
        return ResponseEntity.ok(stats);

    }


}
