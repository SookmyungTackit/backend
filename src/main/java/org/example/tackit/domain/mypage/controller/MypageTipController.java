package org.example.tackit.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.mypage.dto.response.TipMyPostResponseDto;
import org.example.tackit.domain.mypage.dto.response.TipScrapResponse;
import org.example.tackit.domain.mypage.service.MemberService;
import org.example.tackit.domain.mypage.service.MyPageTipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageTipController {
    private final MyPageTipService mypageTipService;
    private final MemberService memberService;

    @GetMapping("/tip_scraps")
    public ResponseEntity<List<TipScrapResponse>> getMyTipScraps(
            @AuthenticationPrincipal CustomUserDetails user) {

        Member member = memberService.getMemberByEmail(user.getEmail());
        List<TipScrapResponse> scraps = mypageTipService.getScrapListByMember(member);
        return ResponseEntity.ok(scraps);
    }

    @GetMapping("/tip_posts")
    public ResponseEntity<List<TipMyPostResponseDto>> getMyTipPosts(
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(mypageTipService.getMyPosts(user.getUsername()));
    }

}
