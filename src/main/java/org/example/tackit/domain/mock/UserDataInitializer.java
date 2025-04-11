package org.example.tackit.domain.mock;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Role;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.free_post.repository.UserJPARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Order(1)
public class UserDataInitializer implements CommandLineRunner {
    private final UserJPARepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.save(Member.builder()
                    .email("yy@sookmyung.ac.kr")
                    .password("1234")
                    .nickname("영신")
                    .role(Role.USER)
                    .joinedYear(2023)
                    .status(Status.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build());

            userRepository.save(Member.builder()
                    .email("noonsong@sookmyung.ac.kr")
                    .password("1234")
                    .nickname("눈송")
                    .role(Role.USER)
                    .joinedYear(2023)
                    .status(Status.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build());
        }
    }
}

