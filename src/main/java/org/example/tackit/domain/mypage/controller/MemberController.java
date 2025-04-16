package org.example.tackit.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.mypage.dto.MemberMypageResponse;
import org.example.tackit.domain.mypage.dto.UpdateNicknameRequest;
import org.example.tackit.domain.mypage.dto.UpdateNicknameResponse;
import org.example.tackit.domain.mypage.service.MemberService;
import org.example.tackit.domain.mypage.service.UpdateMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final UpdateMemberService updateMemberService;

    @GetMapping("/me")
    public ResponseEntity<MemberMypageResponse> getMyPageInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername(); // Spring Security 내부적으로 email이 username

        MemberMypageResponse response = memberService.getMyPageInfo(email);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<UpdateNicknameResponse> updateNickname(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateNicknameRequest request) {
        UpdateNicknameResponse response = updateMemberService.changeNickname(
                userDetails.getUsername(),
                request.getNickname()
        );
        return ResponseEntity.ok(response);
    }



}