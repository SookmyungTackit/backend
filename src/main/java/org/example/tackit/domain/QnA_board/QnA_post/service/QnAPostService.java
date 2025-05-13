package org.example.tackit.domain.QnA_board.QnA_post.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_post.dto.request.QnAPostRequestDto;
import org.example.tackit.domain.QnA_board.QnA_post.dto.request.UpdateQnARequestDto;
import org.example.tackit.domain.QnA_board.QnA_post.dto.response.QnAPostResponseDto;
import org.example.tackit.domain.QnA_board.QnA_post.repository.QnAMemberRepository;
import org.example.tackit.domain.QnA_board.QnA_post.repository.QnAPostRepository;
import org.example.tackit.domain.entity.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAPostService {

    private final QnAPostRepository qnAPostRepository;
    private final QnAMemberRepository qnAMemberRepository;

    // 게시글 작성 (NEWBIE만 가능)
    @Transactional
    public QnAPostResponseDto createPost(QnAPostRequestDto dto, String email) {
        Member member = qnAMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (member.getRole() != Role.NEWBIE) {
            throw new AccessDeniedException("NEWBIE만 질문을 작성할 수 있습니다.");
        }

        QnAPost post = QnAPost.builder()
                .writer(member)
                .title(dto.getTitle())
                .content(dto.getContent())
                .tag(dto.getTag())
                .createdAt(LocalDateTime.now())
                .type(Post.QnA)
                .status(Status.ACTIVE)
                .reportCount(0)
                .build();

        qnAPostRepository.save(post);
        return new QnAPostResponseDto(post);
    }

    // 게시글 수정 (작성자, 관리자만 가능)
    @Transactional
    public QnAPostResponseDto update(long id, UpdateQnARequestDto request, String email){
        Member member = qnAMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        QnAPost post = qnAPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boolean isWriter = post.getWriter().getId().equals(member.getId());
        boolean isAdmin = member.getRole() == Role.ADMIN;

        if (!isWriter && !isAdmin) {
            throw new AccessDeniedException("작성자 또는 관리자만 수정할 수 있습니다.");
        }

        post.update(request.getTitle(), request.getContent(), request.getTag());

        return new QnAPostResponseDto(post);
    }

    // 게시글 삭제 (작성자, 관리자만 가능)
    @Transactional
    public void delete(long id, String email){
        Member member = qnAMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        QnAPost post = qnAPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boolean isWriter = post.getWriter().getId().equals(member.getId());
        boolean isAdmin = member.getRole() == Role.ADMIN;

        if (!isWriter && !isAdmin) {
            throw new AccessDeniedException("작성자 또는 관리자만 삭제할 수 있습니다.");
        }

        qnAPostRepository.delete(post);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<QnAPostResponseDto> findALl(){
        List<QnAPost> posts = qnAPostRepository.findAllByStatus(Status.ACTIVE);
        return posts
                .stream()
                .map(QnAPostResponseDto::new)
                .toList();
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public QnAPostResponseDto getPostById(Long id) {
        QnAPost QnAPost = qnAPostRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return new QnAPostResponseDto(QnAPost);
    }

}
