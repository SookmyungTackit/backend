package org.example.tackit.domain.mypage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.mypage.dto.response.UpdateNicknameResponse;
import org.example.tackit.domain.mypage.dto.response.UpdatePasswordResponse;
import org.example.tackit.domain.mypage.repository.MemberDetailRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateMemberService {
    private final MemberDetailRepository memberDetailRepository;
    private final PasswordEncoder passwordEncoder;

    // 닉네임 변경 서비스
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

    // 비밀번호 변경 서비스
    @Transactional
    public UpdatePasswordResponse updatePassword(String email, String currentPassword, String newPassword) {
        Member member = memberDetailRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));

        // 현재 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        member.changePassword(encodedNewPassword);

        return new UpdatePasswordResponse(true, "비밀번호 변경 성공");
    }


}
