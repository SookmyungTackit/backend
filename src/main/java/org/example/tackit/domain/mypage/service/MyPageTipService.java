package org.example.tackit.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Tip_board.Tip_post.repository.TipPostJPARepository;
import org.example.tackit.domain.Tip_board.Tip_tag.repository.TipPostTagMapRepository;
import org.example.tackit.domain.admin.repository.MemberRepository;
import org.example.tackit.domain.entity.*;
import org.example.tackit.domain.mypage.dto.response.FreeMyPostResponseDto;
import org.example.tackit.domain.mypage.dto.response.FreeScrapResponse;
import org.example.tackit.domain.mypage.dto.response.TipMyPostResponseDto;
import org.example.tackit.domain.mypage.dto.response.TipScrapResponse;
import org.example.tackit.domain.Tip_board.Tip_post.repository.TipScrapRepository;
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageTipService {
    private final TipScrapRepository tipScrapRepository;
    private final MemberRepository memberRepository;
    private final TipPostJPARepository tipPostJPARepository;
    private final TipPostTagMapRepository tipPostTagMapRepository;

    // 스크랩한 tip 게시글 조회
    @Transactional
    public PageResponseDTO<TipScrapResponse> getScrapListByMember(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Page<TipScrap> page = tipScrapRepository.findByMemberAndType(member, Post.Tip, pageable);

        return PageResponseDTO.from(page, scrap -> {
            TipPost post = scrap.getTipPost();

            List<String> tags = tipPostTagMapRepository.findByTipPost(post).stream()
                    .map(mapping -> mapping.getTag().getTagName())
                    .toList();

            return TipScrapResponse.from(scrap, tags);
        });
    }

    // 내가 쓴 tip 게시글 조회
    @Transactional(readOnly = true)
    public PageResponseDTO<TipMyPostResponseDto> getMyPosts(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Page<TipPost> page = tipPostJPARepository.findByWriterAndStatus(member, Status.ACTIVE, pageable);

        return PageResponseDTO.from(page, post -> {
            List<String> tags = tipPostTagMapRepository.findByTipPost(post).stream()
                    .map(mapping -> mapping.getTag().getTagName())
                    .toList();

            return TipMyPostResponseDto.builder()
                    .id(post.getId())
                    .writer(post.getWriter().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .tags(tags)
                    .type(post.getType())
                    .createdAt(post.getCreatedAt())
                    .build();
        });
    }

}
