package org.example.tackit.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_comment.repository.FreeCommentRepository;
import org.example.tackit.domain.Free_board.Free_post.repository.FreePostJPARepository;
import org.example.tackit.domain.Free_board.Free_post.repository.FreeScrapJPARepository;
import org.example.tackit.domain.Free_board.Free_tag.repository.FreePostTagMapRepository;
import org.example.tackit.domain.admin.repository.MemberRepository;
import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.FreeScrap;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.mypage.dto.response.FreeMyCommentResponseDto;
import org.example.tackit.domain.mypage.dto.response.FreeMyPostResponseDto;
import org.example.tackit.domain.mypage.dto.response.FreeScrapResponse;
import org.example.tackit.domain.mypage.dto.response.QnAMyCommentResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageFreeService {
    private final FreeScrapJPARepository freeScrapJPARepository;
    private final MemberRepository memberRepository;
    private final FreePostJPARepository freePostJPARepository;
    private final FreePostTagMapRepository freePostTagMapRepository;
    private final FreeCommentRepository freeCommentRepository;

    public List<FreeScrapResponse> getScrapListByMember(Member member) {
        List<FreeScrap> scraps = freeScrapJPARepository.findByMember(member);

        return scraps.stream()
                .map(FreeScrapResponse::from)
                .collect(Collectors.toList());
    }

    // 내가 쓴 자유 게시글 조회
    @Transactional(readOnly = true)
    public List<FreeMyPostResponseDto> getMyPosts(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return freePostJPARepository.findByWriter(member).stream()
                .filter(post -> post.getStatus() == Status.ACTIVE)
                .map((FreePost post) -> {
                    List<String> tags = freePostTagMapRepository.findByFreePost(post).stream()
                            .map(mapping -> mapping.getTag().getTagName())
                            .toList();
                    return FreeMyPostResponseDto.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .createdAt(post.getCreatedAt())
                            .tags(tags)
                            .build();
                }).toList();
    }

    // 내가 쓴 댓글 조회
    @Transactional(readOnly = true)
    public List<FreeMyCommentResponseDto> getMyComments(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return freeCommentRepository.findByWriter(member).stream()
                .map(comment -> FreeMyCommentResponseDto.builder()
                        .commentId(comment.getId())
                        .postId(comment.getFreePost().getId())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .toList();
    }
}
