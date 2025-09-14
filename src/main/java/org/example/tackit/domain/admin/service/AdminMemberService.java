package org.example.tackit.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.MemberDTO;
import org.example.tackit.domain.admin.dto.MemberStatisticsDTO;
import org.example.tackit.domain.admin.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
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

    public MemberStatisticsDTO getMemberStatistics() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).with(LocalTime.MIN);
        LocalDateTime startOfWeek = now.with(ChronoField.DAY_OF_WEEK, 1).toLocalDate().atStartOfDay();

        long totalCount = memberRepository.countAll();
        long monthlyCount = memberRepository.countJoinedAfter(startOfMonth);
        long weeklyCount = memberRepository.countJoinedAfter(startOfWeek);

        return new MemberStatisticsDTO(totalCount, monthlyCount, weeklyCount);

    }


}
