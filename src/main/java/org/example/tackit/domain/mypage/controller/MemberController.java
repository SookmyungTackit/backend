package org.example.tackit.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.mypage.dto.MemberMypageResponse;
import org.example.tackit.domain.mypage.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberMypageResponse> getMyPageInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername(); // Spring Security 내부적으로 email이 username

        MemberMypageResponse response = memberService.getMyPageInfo(email);
        return ResponseEntity.ok(response);
    }


}