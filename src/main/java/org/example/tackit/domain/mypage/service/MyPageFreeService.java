package org.example.tackit.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_comment.repository.FreeCommentRepository;
import org.example.tackit.domain.Free_board.Free_post.repository.FreePostJPARepository;
import org.example.tackit.domain.Free_board.Free_post.repository.FreeScrapJPARepository;
import org.example.tackit.domain.Free_board.Free_tag.repository.FreePostTagMapRepository;
import org.example.tackit.domain.admin.repository.MemberRepository;
import org.example.tackit.domain.entity.*;
import org.example.tackit.domain.mypage.dto.response.FreeMyCommentResponseDto;
import org.example.tackit.domain.mypage.dto.response.FreeMyPostResponseDto;
import org.example.tackit.domain.mypage.dto.response.FreeScrapResponse;
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageFreeService {
    private final FreeScrapJPARepository freeScrapJPARepository;
    private final MemberRepository memberRepository;
    private final FreePostJPARepository freePostJPARepository;
    private final FreePostTagMapRepository freePostTagMapRepository;
    private final FreeCommentRepository freeCommentRepository;

    // 스크랩한 free 게시글 조회
    @Transactional
    public PageResponseDTO<FreeScrapResponse> getScrapListByMember(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Page<FreeScrap> page = freeScrapJPARepository.findByMemberAndType(member, Post.Free, pageable);


        return PageResponseDTO.from(page, scrap -> {
            FreePost post = scrap.getFreePost();

            List<String> tags = freePostTagMapRepository.findByFreePost(post).stream()
                    .map(mapping -> mapping.getTag().getTagName())
                    .toList();

            return FreeScrapResponse.from(scrap, tags);
        });
    }

    // 내가 쓴 자유 게시글 조회
    @Transactional(readOnly = true)
    public PageResponseDTO<FreeMyPostResponseDto> getMyPosts(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Page<FreePost> page = freePostJPARepository.findByWriter(member, pageable);

        return PageResponseDTO.from(page, post -> {
            List<String> tags = freePostTagMapRepository.findByFreePost(post).stream()
                    .map(mapping -> mapping.getTag().getTagName())
                    .toList();

            return FreeMyPostResponseDto.builder()
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

    // 내가 쓴 댓글 조회
    @Transactional(readOnly = true)
    public PageResponseDTO<FreeMyCommentResponseDto> getMyComments(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Page<FreeComment> comments = freeCommentRepository.findByWriter(member, pageable);

        return PageResponseDTO.from(comments, comment -> FreeMyCommentResponseDto.builder()
                .commentId(comment.getId())
                .postId(comment.getFreePost().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .type(comment.getFreePost().getType())
                .build()
        );
    }
}
