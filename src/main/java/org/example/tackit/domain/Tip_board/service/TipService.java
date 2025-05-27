package org.example.tackit.domain.Tip_board.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_post.repository.FreeMemberJPARepository;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.entity.*;
import org.example.tackit.domain.Tip_board.dto.request.TipPostCreateDTO;
import org.example.tackit.domain.Tip_board.dto.request.TipPostUpdateDTO;
import org.example.tackit.domain.Tip_board.dto.response.TipPostDTO;
import org.example.tackit.domain.Tip_board.repository.TipPostJPARepository;
import org.example.tackit.domain.Tip_board.repository.TipScrapRepository;
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class TipService {
    private final TipPostJPARepository tipPostJPARepository;
    private final FreeMemberJPARepository freeMemberJPARepository;
    private final TipScrapRepository tipScrapRepository;


    public PageResponseDTO<TipPostDTO> getActivePostsByOrganization(String org, Pageable pageable) {
        Page<TipPost> page = tipPostJPARepository.findByOrganizationAndStatus(org, Status.ACTIVE, pageable);

        return PageResponseDTO.from(page, TipPostDTO::fromEntity);
    }

    // [ 게시글 상세 조회 ]
    @Transactional
    public TipPostDTO getPostById(Long id, String org) {
        TipPost tipPost = tipPostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!tipPost.getOrganization().equals(org)) {
            throw new AccessDeniedException("해당 조직의 게시글이 아닙니다.");
        }

        if (!tipPost.getStatus().equals(Status.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비활성화된 게시글입니다.");
        }

        return TipPostDTO.builder()
                .id(tipPost.getId())
                .writer(tipPost.getWriter().getNickname())
                .title(tipPost.getTitle())
                .content(tipPost.getContent())
                .createdAt(tipPost.getCreatedAt())
                .build();
    }

    // [ 게시글 작성 ] : 선임자만 가능
    @Transactional
    public TipPostDTO createPost(TipPostCreateDTO dto, CustomUserDetails user) {
        // 1. 유저 조회
        Member writer = freeMemberJPARepository.findById(user.getId())
                .orElseThrow( () -> new IllegalArgumentException("작성자가 DB에 존재하지 않습니다."));

        if (writer.getRole() != Role.SENIOR) {
            throw new AccessDeniedException("SENIOR만 게시글을 작성할 수 있습니다.");
        }

        // 2. 게시글 생성 : 작성 글, 회원 데이터, 조직 정보
        TipPost newPost = dto.toEntity(writer, user.getOrganization());

        // 3. 게시글 DB 저장 + TipPostDTO로 변환하여 반환
        return TipPostDTO.fromEntity(tipPostJPARepository.save(newPost));
    }

    // [ 게시글 수정 ]
    @Transactional
    public TipPostDTO updatePost(Long id, TipPostUpdateDTO dto, CustomUserDetails user) {
        TipPost post = tipPostJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다.") );

        // 권한 체크 : 요청 유저가 작성자인지
        if(!post.getWriter().getId().equals(user.getId())) {
            throw new AccessDeniedException("해당 게시글을 수정할 권한이 없습니다.");
        }
        post.update(dto.getTitle(), dto.getContent());
        return new TipPostDTO(post);
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
    public void scrapPost(Long id, Long userId) {
        // 1. 게시글 조회
        TipPost post = tipPostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.") );

        // 2. 멤버 조회
        Member member = freeMemberJPARepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 3. 중복 스크랩 방지
        boolean alreadyScrapped = tipScrapRepository.existsByMemberAndTipPost(member, post);
        if (alreadyScrapped) {
            throw new IllegalStateException("이미 스크랩한 게시글입니다.");
        }

        // 4. 스크랩 저장
        TipScrap scrap = new TipScrap(member, post);
        tipScrapRepository.save(scrap);
    }

    // [ 게시글 신고 ]
    @Transactional
    public void increasePostReportCount(Long id) {
        TipPost post = tipPostJPARepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        post.increaseReportCount();
    }

}
