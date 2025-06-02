package org.example.tackit.domain.Free_board.Free_post.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_post.dto.request.FreePostReqDto;
import org.example.tackit.domain.Free_board.Free_post.dto.request.UpdateFreeReqDto;
import org.example.tackit.domain.Free_board.Free_post.dto.response.FreePostRespDto;
import org.example.tackit.domain.Free_board.Free_post.repository.FreeMemberJPARepository;
import org.example.tackit.domain.Free_board.Free_post.repository.FreePostJPARepository;
import org.example.tackit.domain.Free_board.Free_post.repository.FreeScrapJPARepository;
import org.example.tackit.domain.Free_board.Free_tag.repository.FreePostTagMapRepository;
import org.example.tackit.domain.entity.*;
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FreePostService {
    private final FreePostJPARepository freePostJPARepository;
    private final FreeMemberJPARepository freeMemberJPARepository;
    private final FreePostTagService freeService;
    private final FreeScrapJPARepository freeScrapJPARepository;
    private final FreePostTagMapRepository freePostTagMapRepository;

    // [ 게시글 전체 조회 ]
    @Transactional
    public PageResponseDTO<FreePostRespDto> findAll(String org, Pageable pageable ) {
        Page<FreePost> page = freePostJPARepository.findByOrganizationAndStatus(org, Status.ACTIVE, pageable);

        return PageResponseDTO.from(page, post -> {
            List<String> tags = freePostTagMapRepository.findByFreePost(post).stream()
                    .map(mapping -> mapping.getTag().getTagName())
                    .toList();

            return FreePostRespDto.builder()
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
    public FreePostRespDto getPostById(Long id, String org) {
        FreePost post = freePostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getOrganization().equals(org)) {
            throw new AccessDeniedException("해당 조직의 게시글이 아닙니다.");
        }

        if (!post.getStatus().equals(Status.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비활성화된 게시글입니다.");
        }

        List<String> tagNames = freeService.getTagNamesByPost(post);

        return FreePostRespDto.builder()
                .id(post.getId())
                .writer(post.getWriter().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(tagNames)
                .createdAt(post.getCreatedAt())
                .build();
    }

    // [ 게시글 작성 ]
    @Transactional
    public FreePostRespDto createPost(FreePostReqDto dto, String email, String org) {
        // 1. 유저 조회
        Member member = freeMemberJPARepository.findByEmailAndOrganization(email, org)
                .orElseThrow( () -> new IllegalArgumentException("작성자가 DB에 존재하지 않습니다."));

        // 2. 게시글 생성
        FreePost post = FreePost.builder()
                        .writer(member)
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .createdAt(LocalDateTime.now())
                        .type(Post.Free)
                        .status(Status.ACTIVE)
                        .reportCount(0)
                        .organization(org)
                        .build();

        freePostJPARepository.save(post);

        List<String> tagNames = freeService.assignTagsToPost(post, dto.getTagIds());

        return FreePostRespDto.builder()
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
    public FreePostRespDto update(Long id, UpdateFreeReqDto req, String email, String org) {
        Member member = freeMemberJPARepository.findByEmailAndOrganization(email, org)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        FreePost post = freePostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boolean isWriter = post.getWriter().getId().equals(member.getId());

        if (!isWriter) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }

        post.update(req.getTitle(), req.getContent());

        freeService.deleteTagsByPost(post);
        List<String> tagNames = freeService.assignTagsToPost(post, req.getTagIds());

        return FreePostRespDto.builder()
                .id(post.getId())
                .writer(post.getWriter().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .tags(tagNames)
                .build();
    }

    // [ 게시글 삭제 ] : 작성자, 관리자만
    @Transactional
    public void delete(Long id, String email, String org) {
        Member member = freeMemberJPARepository.findByEmailAndOrganization(email, org)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        FreePost post = freePostJPARepository.findById(id)
                 .orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boolean isWriter = post.getWriter().getId().equals(member.getId());
        boolean isAdmin = member.getRole() == Role.ADMIN;

        if (!isAdmin && !isWriter) {
            throw new AccessDeniedException("작성자 또는 관리자만 삭제할 수 있습니다.");
        }

        post.delete(); // Soft Deleted
    }

    // [ 게시글 신고 ]
    @Transactional
    public void increasePostReportCount(Long id) {
        FreePost post = freePostJPARepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));


        post.increaseReportCount();
    }

    // [ 게시글 스크랩 ]
    @Transactional
    public String toggleScrap(Long id, Long userId) {
        // 1. 게시글 조회
        FreePost post = freePostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.") );

        // 2. 멤버 조회
        Member member = freeMemberJPARepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.") );

        Optional<FreeScrap> existing = freeScrapJPARepository.findByMemberAndFreePost(member, post);

        if (existing.isPresent()) {
            freeScrapJPARepository.delete(existing.get());
            return "게시글 스크랩을 취소하였습니다.";
        } else {
            FreeScrap scrap = new FreeScrap(member, post);
            freeScrapJPARepository.save(scrap);
            return "게시글을 스크랩하였습니다.";
        }
    }

}
