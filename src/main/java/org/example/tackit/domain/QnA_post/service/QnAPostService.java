package org.example.tackit.domain.QnA_post.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_post.dto.request.QnAPostRequestDto;
import org.example.tackit.domain.QnA_post.dto.response.QnAPostResponseDto;
import org.example.tackit.domain.QnA_post.repository.QnAMemberRepository;
import org.example.tackit.domain.QnA_post.repository.QnAPostRepository;
import org.example.tackit.domain.entity.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QnAPostService {

    private final QnAPostRepository qnAPostRepository;
    private final QnAMemberRepository qnAMemberRepository;

    @Transactional
    public QnAPostResponseDto createPost(QnAPostRequestDto dto) {
        Member member = qnAMemberRepository.findByNickname(dto.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("작성자가 존재하지 않습니다."));

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
}
