package org.example.tackit.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.mypage.dto.response.TipMyPostResponseDto;
import org.example.tackit.domain.mypage.dto.response.TipScrapResponse;
import org.example.tackit.domain.mypage.service.MyPageTipService;
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageTipController {
    private final MyPageTipService mypageTipService;

    @GetMapping("/tip-scraps")
    public ResponseEntity<PageResponseDTO<TipScrapResponse>> getMyTipScraps(
            @AuthenticationPrincipal CustomUserDetails user,
            @PageableDefault(size = 5, sort = "savedAt", direction = Sort.Direction.DESC) Pageable pageable
            ) {
            return ResponseEntity.ok(mypageTipService.getScrapListByMember(user.getEmail(), pageable));
    }

    @GetMapping("/tip-posts")
    public ResponseEntity<PageResponseDTO<TipMyPostResponseDto>> getMyTipPosts(
            @AuthenticationPrincipal CustomUserDetails user,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(mypageTipService.getMyPosts(user.getEmail(), pageable));
    }
}
