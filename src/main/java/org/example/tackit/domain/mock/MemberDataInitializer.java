package org.example.tackit.domain.mock;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.repository.MemberRepository;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Role;
import org.example.tackit.domain.entity.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Order(1)
public class MemberDataInitializer implements CommandLineRunner {
    private final MemberRepository memberRepository;

    @Override
    public void run(String... args) throws Exception {
        if (memberRepository.count() == 0) {
            memberRepository.save(Member.builder()
                    .email("yy@sookmyung.ac.kr")
                    .password("1234")
                    .nickname("영신")
                    .organization("시종설-1팀")
                    .role(Role.NEWBIE)
                    .joinedYear(2025)
                    .status(Status.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build());

            memberRepository.save(Member.builder()
                    .email("noonsong@sookmyung.ac.kr")
                    .password("1234")
                    .nickname("눈송")
                    .organization("시종설-1팀")
                    .role(Role.SENIOR)
                    .joinedYear(2023)
                    .status(Status.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build());

            memberRepository.save(Member.builder()
                    .email("capstone@sookmyung.ac.kr")
                    .password("1234")
                    .nickname("시종설")
                    .organization("시종설-2팀")
                    .role(Role.SENIOR)
                    .joinedYear(2023)
                    .status(Status.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build());
        }
    }
}

