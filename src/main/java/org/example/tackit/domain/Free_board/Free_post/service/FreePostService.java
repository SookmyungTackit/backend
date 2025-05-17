package org.example.tackit.domain.Free_board.Free_post.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_post.dto.request.FreePostReqDto;
import org.example.tackit.domain.Free_board.Free_post.dto.request.UpdateFreeReqDto;
import org.example.tackit.domain.Free_board.Free_post.dto.response.FreePostRespDto;
import org.example.tackit.domain.Free_board.Free_post.repository.FreeMemberJPARepository;
import org.example.tackit.domain.Free_board.Free_post.repository.FreePostJPARepository;
import org.example.tackit.domain.Free_board.Free_tag.service.FreeTagService;
import org.example.tackit.domain.entity.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreePostService {
    private final FreePostJPARepository freePostJPARepository;
    private final FreeMemberJPARepository freeMemberJPARepository;
    private final FreeTagService freeTagService;

    // [ 게시글 전체 조회 ]
    @Transactional
    public List<FreePostRespDto> findAll(String org) {
        List<FreePost> posts = freePostJPARepository.findByOrganizationAndStatus(org, Status.ACTIVE);

        return posts.stream()
                .map(post -> {
                    List<String> tagNames = freeTagService.getTagNamesByPost(post);

                    return FreePostRespDto.builder()
                            .id(post.getId())
                            .writer(post.getWriter().getNickname())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .createdAt(post.getCreatedAt())
                            .tags(tagNames)
                            .build();
                })
                .toList();
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

        List<String> tagNames = freeTagService.getTagNamesByPost(post);

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

        List<String> tagNames = freeTagService.assignTagsToPost(post, dto.getTagIds());

        return FreePostRespDto.builder()
                .id(post.getId())
                .writer(member.getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .tags(tagNames)
                .build();

    }

    // [ 게시글 수정 ] : 작성자, 관리자만
    @Transactional
    public FreePostRespDto update(Long id, UpdateFreeReqDto req, String email, String org) {
        Member member = freeMemberJPARepository.findByEmailAndOrganization(email, org)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        FreePost post = freePostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boolean isWriter = post.getWriter().getId().equals(member.getId());
        boolean isAdmin = member.getRole() == Role.ADMIN;

        if (!isAdmin && !isWriter) {
            throw new AccessDeniedException("작성자 또는 관리자만 수정할 수 있습니다.");
        }

        post.update(req.getTitle(), req.getContent());

        freeTagService.deleteTagsByPost(post);
        List<String> tagNames = freeTagService.assignTagsToPost(post, req.getTagIds());

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

}
