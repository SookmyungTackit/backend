package org.example.tackit.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Tip_board.repository.TipPostJPARepository;
import org.example.tackit.domain.admin.repository.MemberRepository;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipScrap;
import org.example.tackit.domain.mypage.dto.response.QnAMyPostResponseDto;
import org.example.tackit.domain.mypage.dto.response.TipMyPostResponseDto;
import org.example.tackit.domain.mypage.dto.response.TipScrapResponse;
import org.example.tackit.domain.Tip_board.repository.TipScrapRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageTipService {
    private final TipScrapRepository tipScrapRepository;
    private final MemberRepository memberRepository;
    private final TipPostJPARepository tipPostJPARepository;

    public List<TipScrapResponse> getScrapListByMember(Member member) {
        List<TipScrap> scraps = tipScrapRepository.findByMember(member);
        return scraps.stream()
                .map(TipScrapResponse::from)
                .collect(Collectors.toList());
    }

    // 내가 쓴 tip 게시글 조회
    @Transactional(readOnly = true)
    public List<TipMyPostResponseDto> getMyPosts(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return tipPostJPARepository.findByWriter(member).stream()
                .filter(post -> post.getStatus() == Status.ACTIVE)
                .map(post -> TipMyPostResponseDto.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .build()
                ).toList();
    }
}
