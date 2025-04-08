package org.example.tackit.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.UserDTO;
import org.example.tackit.domain.admin.repository.UserRepository;
import org.example.tackit.domain.entity.Status;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;

    public List<UserDTO> getAllUsersOrderByStatus() {
        return userRepository.findAllOrderByStatus().stream()
                .map(user -> UserDTO.builder()
                        .nickname(user.getNickname())
                        .email(user.getEmail())
                        .status(user.getStatus())
                        .createdAt(user.getCreatedAt().toLocalDate())
                        .build())
                .collect(Collectors.toList());
    }
}
