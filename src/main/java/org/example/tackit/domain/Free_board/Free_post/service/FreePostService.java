package org.example.tackit.domain.Free_board.Free_post.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.tackit.config.S3.S3UploadService;
import org.example.tackit.domain.Free_board.Free_post.dto.request.FreePostReqDto;
import org.example.tackit.domain.Free_board.Free_post.dto.request.UpdateFreeReqDto;
import org.example.tackit.domain.Free_board.Free_post.dto.response.FreePostRespDto;
import org.example.tackit.domain.Free_board.Free_post.repository.*;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FreePostService {
    private final FreePostJPARepository freePostJPARepository;
    private final FreeMemberJPARepository freeMemberJPARepository;
    private final FreePostTagService tagService;
    private final FreeScrapJPARepository freeScrapJPARepository;
    private final FreePostTagMapRepository freePostTagMapRepository;
    private final FreePostReportRepository freePostReportRepository;
    private final S3UploadService s3UploadService;
    private final FreePostImageRepository freePostImageRepository;

    // [ 게시글 전체 조회 ]
    @Transactional
    public PageResponseDTO<FreePostRespDto> findAll(String org, Pageable pageable ) {
        Page<FreePost> page = freePostJPARepository.findByOrganizationAndStatus(org, Status.ACTIVE, pageable);

        return PageResponseDTO.from(page, post -> {
            List<String> tags = freePostTagMapRepository.findByFreePost(post).stream()
                    .map(mapping -> mapping.getTag().getTagName())
                    .toList();

            String imageUrl = post.getImages().isEmpty() ? null
                    : post.getImages().get(0).getImageUrl();

            return FreePostRespDto.builder()
                    .id(post.getId())
                    .writer(post.getWriter().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .tags(tags)
                    .imageUrl(imageUrl)
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

        List<String> tagNames = tagService.getTagNamesByPost(post);

        String imageUrl = post.getImages().isEmpty() ? null
                : post.getImages().get(0).getImageUrl();

        return FreePostRespDto.builder()
                .id(post.getId())
                .writer(post.getWriter().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(tagNames)
                .imageUrl(imageUrl)
                .createdAt(post.getCreatedAt())
                .build();
    }

    // [ 게시글 작성 ]
    @Transactional
    public FreePostRespDto createPost(FreePostReqDto dto, String email, String org) throws IOException {
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

        // 3. 이미지 업로드 → PostImage 저장
        String imageUrl = null;
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            imageUrl = s3UploadService.saveFile(dto.getImage());

            FreePostImage image = FreePostImage.builder()
                    .imageUrl(imageUrl)
                    .freePost(post)
                    .build();

            freePostImageRepository.save(image); // 따로 JPARepository 필요
        }

        List<String> tagNames = tagService.assignTagsToPost(post, dto.getTagIds());

        return FreePostRespDto.builder()
                .id(post.getId())
                .writer(member.getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .tags(tagNames)
                .imageUrl(imageUrl)
                .build();

    }

    // [ 게시글 수정 ] : 작성자만
    @Transactional
    public FreePostRespDto update(Long id, UpdateFreeReqDto req, String email, String org) throws IOException {
        Member member = freeMemberJPARepository.findByEmailAndOrganization(email, org)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        FreePost post = freePostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boolean isWriter = post.getWriter().getId().equals(member.getId());

        if (!isWriter) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }

        post.update(req.getTitle(), req.getContent());

        tagService.deleteTagsByPost(post);
        List<String> tagNames = tagService.assignTagsToPost(post, req.getTagIds());

        String imageUrl = null;
        // 1. "이미지 제거" 요청
        if (req.isRemoveImage()) {
            freePostImageRepository.findByFreePostId(post.getId())
                    .forEach(oldImage -> {
                        s3UploadService.deleteImage(oldImage.getImageUrl()); // S3 삭제
                        freePostImageRepository.delete(oldImage);           // DB 삭제
                    });
        }

        // 2. 새 이미지 업로드
        else if (req.getImage() != null && !req.getImage().isEmpty()) {
            // 기존 이미지 제거
            freePostImageRepository.findByFreePostId(post.getId())
                    .forEach(oldImage -> {
                        s3UploadService.deleteImage(oldImage.getImageUrl());
                        freePostImageRepository.delete(oldImage);
                    });

            // 새 이미지 저장
            imageUrl = s3UploadService.saveFile(req.getImage());
            FreePostImage newImage = FreePostImage.builder()
                    .imageUrl(imageUrl)
                    .freePost(post)
                    .build();

            freePostImageRepository.save(newImage);
        }

        // 3. 아무 요청 없으면 기존 이미지 유지
        else {
            List<FreePostImage> images = freePostImageRepository.findByFreePostId(post.getId());
            if (!images.isEmpty()) {
                imageUrl = images.get(0).getImageUrl();
            }
        }

        return FreePostRespDto.builder()
                .id(post.getId())
                .writer(post.getWriter().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .tags(tagNames)
                .imageUrl(imageUrl)
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
    public String report(Long postId, Long userId) {
        FreePost post = freePostJPARepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        Member member = freeMemberJPARepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.") );

        boolean alreadyReported = freePostReportRepository.existsByMemberAndFreePost(member, post);

        if (alreadyReported) {
            return "이미 신고한 게시글입니다.";
        }
        freePostReportRepository.save(
                FreeReport.builder()
                        .member(member)
                        .freePost(post)
                        .build()
        );
        // 신고 횟수 증가
        post.increaseReportCount();
        return "게시글을 신고하였습니다.";
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
