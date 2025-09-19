package org.example.tackit.domain.mypage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Role;
import org.example.tackit.domain.mypage.repository.MemberDetailRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberScheduler {

    private final MemberDetailRepository memberDetailRepository;

    // 매년 1월 1일 0시 0분 0초에 실행
    @Transactional
    @Scheduled(cron = "0 0 0 1 1 *")
    public void updateSeniorMembers() {
        int currentYear = LocalDate.now().getYear();
        int updatedCount = memberDetailRepository.bulkUpdateRole(Role.NEWBIE, Role.SENIOR, currentYear);
        System.out.println("업데이트된 멤버 수 = " + updatedCount);
    }

}

