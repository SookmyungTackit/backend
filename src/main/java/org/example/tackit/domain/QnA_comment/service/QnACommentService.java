package org.example.tackit.domain.QnA_comment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_comment.dto.request.QnACommentCreateDto;
import org.example.tackit.domain.QnA_comment.dto.request.QnACommentUpdateDto;
import org.example.tackit.domain.QnA_comment.dto.response.QnACommentResponseDto;
import org.example.tackit.domain.QnA_comment.repository.QnACommentRepository;
import org.example.tackit.domain.QnA_post.repository.QnAMemberRepository;
import org.example.tackit.domain.QnA_post.repository.QnAPostRepository;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.QnAComment;
import org.example.tackit.domain.entity.QnAPost;
import org.example.tackit.domain.entity.Role;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnACommentService {
    private final QnACommentRepository qnACommentRepository;
    private final QnAPostRepository qnAPostRepository;
    private final QnAMemberRepository qnAMemberRepository;

    // 댓글 생성 (SENIOR만 가능)
    @Transactional
    public QnACommentResponseDto createComment(QnACommentCreateDto dto, String email){
        Member member = qnAMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (member.getRole() != Role.SENIOR) {
            throw new AccessDeniedException("SENIOR만 댓글을 작성할 수 있습니다.");
        }

        QnAPost post = qnAPostRepository.findById(dto.getQnaPostId())
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        QnAComment comment = QnAComment.builder()
                .writer(member)
                .qnAPost(post)
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        return new QnACommentResponseDto(qnACommentRepository.save(comment));
    }


    // 전체 댓글 조회
    @Transactional (readOnly = true)
    public List<QnACommentResponseDto> getCommentByPost(long postId){
        QnAPost post = qnAPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        return qnACommentRepository.findByQnAPost(post)
                .stream()
                .map(QnACommentResponseDto::new)
                .toList();
    }

    // 댓글 수정 (작성자, 관리자만 가능)
    @Transactional
    public QnACommentResponseDto updateComment(long commentId, QnACommentUpdateDto dto, String email){
        Member member = qnAMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));


        QnAComment comment = qnACommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        boolean isWriter = comment.getWriter().getId().equals(member.getId());
        boolean isAdmin = member.getRole() == Role.ADMIN;

        if (!isWriter && !isAdmin) {
            throw new AccessDeniedException("작성자 또는 관리자만 수정할 수 있습니다.");
        }

        comment.updateContent(dto.getContent());

        return new QnACommentResponseDto(comment);
    }

    // 댓글 삭제 (작성자, 관리자만 가능)
    @Transactional
    public void deleteComment(long commentId, String email){
        Member member = qnAMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        QnAComment comment = qnACommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        boolean isWriter = comment.getWriter().getId().equals(member.getId());
        boolean isAdmin = member.getRole() == Role.ADMIN;

        if (!isWriter && !isAdmin) {
            throw new AccessDeniedException("작성자 또는 관리자만 삭제할 수 있습니다.");
        }

        qnACommentRepository.delete(comment);
    }

}
