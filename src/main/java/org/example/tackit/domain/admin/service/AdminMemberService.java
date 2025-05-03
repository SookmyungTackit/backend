package org.example.tackit.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.MemberDTO;
import org.example.tackit.domain.admin.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminMemberService {
    private final MemberRepository memberRepository;

    public List<MemberDTO> getAllMembersOrderByStatus() {
        return memberRepository.findAllOrderByStatus().stream()
                .map(member -> MemberDTO.builder()
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .organization(member.getOrganization())
                        .status(member.getStatus())
                        .createdAt(member.getCreatedAt().toLocalDate())
                        .build())
                .collect(Collectors.toList());
    }


}
