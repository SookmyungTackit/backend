package org.example.tackit.domain.QnA_post.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_post.dto.request.QnAPostRequestDto;
import org.example.tackit.domain.QnA_post.dto.request.UpdateQnARequestDto;
import org.example.tackit.domain.QnA_post.dto.response.QnAPostResponseDto;
import org.example.tackit.domain.QnA_post.repository.QnAMemberRepository;
import org.example.tackit.domain.QnA_post.repository.QnAPostRepository;
import org.example.tackit.domain.entity.*;
import org.example.tackit.domain.free_post.dto.response.FreePostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnAPostService {

    private final QnAPostRepository qnAPostRepository;
    private final QnAMemberRepository qnAMemberRepository;

    // 게시글 작성
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

    // 게시글 수정
    @Transactional
    public QnAPostResponseDto update(long id, UpdateQnARequestDto request){
        QnAPost post = qnAPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성자가 존재하지 않습니다."));

        post.update(request.getTitle(), request.getContent(), request.getTag());

        return new QnAPostResponseDto(post);
    }

    // 게시글 삭제
    public void delete(long id){
        qnAPostRepository.deleteById(id);
    }

    // 게시글 전체 조회
    public List<QnAPostResponseDto> findALl(){
        List<QnAPost> posts = qnAPostRepository.findAllByStatus(Status.ACTIVE);
        return posts.stream()
                .map(QnAPostResponseDto::new)
                .toList();
    }

    // 게시글 상세 조회
    public QnAPostResponseDto getPostById(Long id) {
        QnAPost QnAPost = qnAPostRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return new QnAPostResponseDto(QnAPost);
    }

}
