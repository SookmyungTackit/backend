package org.example.tackit.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.mypage.dto.response.FreeScrapResponse;
import org.example.tackit.domain.mypage.service.MemberService;
import org.example.tackit.domain.mypage.service.MyPageFreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mypage/free-scraps")
@RequiredArgsConstructor
public class MypageFreeController {
    private final MyPageFreeService myPageFreeService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<FreeScrapResponse>> getMyFreeScraps(
            @AuthenticationPrincipal CustomUserDetails user) {

        Member member = memberService.getMemberByEmail(user.getEmail());
        List<FreeScrapResponse> scraps = myPageFreeService.getScrapListByMember(member);
        return ResponseEntity.ok(scraps);
    }

}
