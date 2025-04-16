package org.example.tackit.domain.mypage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.mypage.dto.UpdateNicknameRequest;
import org.example.tackit.domain.mypage.dto.UpdateNicknameResponse;
import org.example.tackit.domain.mypage.repository.MemberDetailRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateMemberService {
    private final MemberDetailRepository memberDetailRepository;


    @Transactional
    public UpdateNicknameResponse changeNickname(String email, String newNickname) {
        Member member = memberDetailRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));

        if (memberDetailRepository.existsByNickname(newNickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        String before = member.getNickname(); // 변경 전 닉네임 저장
        member.updateNickname(newNickname);   // 도메인에게 변경 위임

        return new UpdateNicknameResponse(before, newNickname);
    }

}
