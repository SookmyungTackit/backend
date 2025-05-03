package org.example.tackit.domain.auth.login.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.auth.login.repository.UserRepository;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RejoinCheckService {
    private final UserRepository userRepository;

    // 회원 상태 조회
    public String checkEmailStatus(String email) {
        if (userRepository.existsByEmailAndStatus(email, Status.DELETED)) {
            return "DELETED";
        } else if (userRepository.existsByEmailAndStatus(email, Status.ACTIVE)) {
            return "ACTIVE";
        } else {
            return "AVAILABLE";
        }
    }

    // 탈퇴한 회원 삭제
    public boolean deleteDeletedMember(String email) {
        Optional<Member> deletedMember = userRepository.findByEmailAndStatus(email, Status.DELETED);
        if (deletedMember.isPresent()) {
            userRepository.delete(deletedMember.get());
            return true;
        }
        return false;
    }

}
