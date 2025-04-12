package org.example.tackit.domain.login.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.login.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckService {

    private final UserRepository userRepository;

    public boolean isEmailDuplicated(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isNicknameDuplicated(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}

