package org.example.tackit.domain.Free_board.Free_comment.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_comment.dto.req.FreeCommentCreateDto;
import org.example.tackit.domain.Free_board.Free_comment.dto.req.FreeCommentUpdateDto;
import org.example.tackit.domain.Free_board.Free_comment.dto.resp.FreeCommentRespDto;
import org.example.tackit.domain.Free_board.Free_comment.repository.FreeCommentRepository;
import org.example.tackit.domain.Free_board.Free_post.repository.FreeMemberJPARepository;
import org.example.tackit.domain.Free_board.Free_post.repository.FreePostJPARepository;
import org.example.tackit.domain.QnA_board.QnA_comment.dto.response.QnACommentResponseDto;
import org.example.tackit.domain.entity.FreeComment;
import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Role;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeCommentService {
    private final FreeCommentRepository freeCommentRepository;
    private final FreePostJPARepository freePostJPARepository;
    private final FreeMemberJPARepository freeMemberJPARepository;

    // [ 댓글 생성 ]
    @Transactional
    public FreeCommentRespDto createComment(FreeCommentCreateDto dto, String email, String org){
        Member member = freeMemberJPARepository.findByEmailAndOrganization(email, org)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        FreePost post = freePostJPARepository.findById(dto.getFreePostId())
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        FreeComment comment = FreeComment.builder()
                .writer(member)
                .freePost(post)
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        return new FreeCommentRespDto(freeCommentRepository.save(comment));
    }

    // [ 게시글 댓글 조회 ]
    @Transactional
    public List<FreeCommentRespDto> getCommentByPost(Long postId, String org){
        FreePost post = freePostJPARepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        if (!post.getWriter().getOrganization().equals(org)) {
            throw new AccessDeniedException("해당 조직의 게시글만 조회할 수 있습니다.");
        }

        return freeCommentRepository.findByFreePost(post)
                .stream()
                .map(FreeCommentRespDto::new)
                .toList();
    }

    // [ 댓글 수정 ] : 작성자만 가능
    @Transactional
    public FreeCommentRespDto updateComment(Long commentId, FreeCommentUpdateDto dto, String email, String org){
        Member member = freeMemberJPARepository.findByEmailAndOrganization(email, org)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        FreeComment comment = freeCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        boolean isWriter = comment.getWriter().getId().equals(member.getId());

        if (!isWriter) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }

        comment.updateContent(dto.getContent());

        return new FreeCommentRespDto(comment);
    }

    // [ 댓글 삭제 ] : 작성자, 관리자만 가능
    @Transactional
    public void deleteComment(Long commentId, String email, String org){
        Member member = freeMemberJPARepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        FreeComment comment = freeCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        boolean isWriter = comment.getWriter().getId().equals(member.getId());
        boolean isAdmin = member.getRole() == Role.ADMIN;

        if (!isWriter && !isAdmin) {
            throw new AccessDeniedException("작성자 또는 관리자만 삭제할 수 있습니다.");
        }

        // Hard Delete
        freeCommentRepository.delete(comment);
    }

    // [ 댓글 신고 ]
    @Transactional
    public void increaseCommentReportCount(long id, String org){
        FreeComment comment = freeCommentRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!comment.getWriter().getOrganization().equals(org)) {
            throw new AccessDeniedException("해당 조직의 댓글만 신고할 수 있습니다.");
        }
        comment.increaseReportCount();

        if (comment.getReportCount() >= 3) {
            freeCommentRepository.delete(comment); // Hard Delete
        }
    }




}
