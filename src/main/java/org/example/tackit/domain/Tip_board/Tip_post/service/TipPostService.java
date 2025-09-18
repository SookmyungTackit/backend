package org.example.tackit.domain.Tip_board.Tip_post.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Tip_board.Tip_post.dto.response.TipPostRespDto;
import org.example.tackit.domain.Tip_board.Tip_post.repository.TipMemberJPARepository;
import org.example.tackit.domain.Tip_board.Tip_post.repository.TipPostReportRepository;
import org.example.tackit.domain.Tip_board.Tip_tag.repository.TipPostTagMapRepository;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.entity.*;
import org.example.tackit.domain.Tip_board.Tip_post.dto.request.TipPostReqDto;
import org.example.tackit.domain.Tip_board.Tip_post.dto.request.TipPostUpdateDto;
import org.example.tackit.domain.Tip_board.Tip_post.repository.TipPostJPARepository;
import org.example.tackit.domain.Tip_board.Tip_post.repository.TipScrapRepository;
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TipPostService {
    private final TipPostJPARepository tipPostJPARepository;
    private final TipMemberJPARepository tipMemberJPARepository;
    private final TipScrapRepository tipScrapRepository;
    private final TipPostReportRepository tipPostReportRepository;
    private final TipPostTagMapRepository tipPostTagMapRepository;
    private final TipTagService tagService;

    public PageResponseDTO<TipPostRespDto> getActivePostsByOrganization(String org, Pageable pageable) {
        Page<TipPost> page = tipPostJPARepository.findByOrganizationAndStatus(org, Status.ACTIVE, pageable);

        return PageResponseDTO.from(page, post -> {
                    List<String> tags = tipPostTagMapRepository.findByTipPost(post).stream()
                            .map(mapping -> mapping.getTag().getTagName())
                            .toList();

                    return TipPostRespDto.builder()
                            .id(post.getId())
                            .writer(post.getWriter().getNickname())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .createdAt(post.getCreatedAt())
                            .tags(tags)
                            .build();
        });
    }

    // [ 게시글 상세 조회 ]
    @Transactional
    public TipPostRespDto getPostById(Long id, String org) {
        TipPost tipPost = tipPostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!tipPost.getOrganization().equals(org)) {
            throw new AccessDeniedException("해당 조직의 게시글이 아닙니다.");
        }

        if (!tipPost.getStatus().equals(Status.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비활성화된 게시글입니다.");
        }

        List<String> tagNames = tagService.getTagNamesByPost(tipPost);

        return TipPostRespDto.builder()
                .id(tipPost.getId())
                .writer(tipPost.getWriter().getNickname())
                .title(tipPost.getTitle())
                .content(tipPost.getContent())
                .tags(tagNames)
                .createdAt(tipPost.getCreatedAt())
                .build();
    }

    // [ 게시글 작성 ] : 선임자만 가능
    @Transactional
    public TipPostRespDto createPost(TipPostReqDto dto, String email, String org) {
        // 1. 유저 조회
        Member member = tipMemberJPARepository.findByEmailAndOrganization(email, org)
                .orElseThrow(() -> new IllegalArgumentException("작성자가 DB에 존재하지 않습니다."));

        if (member.getRole() != Role.SENIOR) {
            throw new AccessDeniedException("SENIOR만 게시글을 작성할 수 있습니다.");
        }

        // 2. 게시글 생성
        TipPost post = TipPost.builder()
                .writer(member)
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .type(Post.Tip)
                .status(Status.ACTIVE)
                .reportCount(0)
                .organization(org)
                .build();

        tipPostJPARepository.save(post);

        List<String> tagNames = tagService.assignTagsToPost(post, dto.getTagIds());

        return TipPostRespDto.builder()
                .id(post.getId())
                .writer(member.getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .tags(tagNames)
                .build();
    }

    // [ 게시글 수정 ] : 작성자만
    @Transactional
    public TipPostRespDto update(Long id, TipPostUpdateDto dto, String email, String org) {
        Member member = tipMemberJPARepository.findByEmailAndOrganization(email, org)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        TipPost post = tipPostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boolean isWriter = post.getWriter().getId().equals(member.getId());

        if (!isWriter) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }

        post.update(dto.getTitle(), dto.getContent());

        tagService.deleteTagsByPost(post);
        List<String> tagNames = tagService.assignTagsToPost(post, dto.getTagIds());

        return TipPostRespDto.builder()
                .id(post.getId())
                .writer(post.getWriter().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .tags(tagNames)
                .build();
    }

    // [ 게시글 삭제 ]
    @Transactional
    public void deletePost(Long id, CustomUserDetails user) {
        TipPost post = tipPostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        // 권한 체크 : 요청 유저가 작성자인지
        if(!post.getWriter().getId().equals(user.getId())) {
            throw new AccessDeniedException("해당 게시글을 수정할 권한이 없습니다.");
        }
        post.delete();
    }

    // [ 게시글 스크랩 ]
    @Transactional
    public String toggleScrap(Long id, Long userId) {
        // 1. 게시글 조회
        TipPost post = tipPostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.") );

        // 2. 멤버 조회
        Member member = tipMemberJPARepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.") );

        Optional<TipScrap> existing = tipScrapRepository.findByMemberAndTipPost(member, post);

        if (existing.isPresent()) {
            tipScrapRepository.delete(existing.get());
            return "게시글 스크랩을 취소하였습니다.";
        } else {
            TipScrap scrap = new TipScrap(member, post);
            tipScrapRepository.save(scrap);
            return "게시글을 스크랩하였습니다.";
        }
    }

    // [ 게시글 신고 ]
    @Transactional
    public String report(Long postId, Long userId) {
        TipPost post = tipPostJPARepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        Member member = tipMemberJPARepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.") );

        boolean alreadyReported = tipPostReportRepository.existsByMemberAndTipPost(member, post);

        if (alreadyReported) {
            return "이미 신고한 게시글입니다.";
        }
        tipPostReportRepository.save(
                TipReport.builder()
                        .member(member)
                        .tipPost(post)
                        .build()
        );
        // 신고 횟수 증가
        post.increaseReportCount();
        return "게시글을 신고하였습니다.";
    }

}
