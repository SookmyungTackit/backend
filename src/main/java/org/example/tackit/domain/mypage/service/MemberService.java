package org.example.tackit.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.mypage.dto.response.MemberMypageResponse;
import org.example.tackit.domain.mypage.repository.MemberDetailRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberDetailRepository memberDetailRepository;

    // 멤버 객체에게 응답 생성 책임 위임
    public MemberMypageResponse getMyPageInfo(String email) {
        System.out.println("입력된 이메일: " + email);
        Member member = memberDetailRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
        return member.generateMypageResponse();
    }

}
