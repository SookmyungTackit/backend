package org.example.tackit.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_comment.repository.QnACommentRepository;
import org.example.tackit.domain.QnA_board.QnA_post.repository.QnAMemberRepository;
import org.example.tackit.domain.QnA_board.QnA_post.repository.QnAPostRepository;
import org.example.tackit.domain.QnA_board.QnA_tag.repository.QnAPostTagMapRepository;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.mypage.dto.response.QnAMyCommentResponseDto;
import org.example.tackit.domain.mypage.dto.response.QnAMyPostResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageQnAService {
    private final QnAMemberRepository qnAMemberRepository;
    private final QnAPostRepository qnAPostRepository;
    private final QnACommentRepository qnACommentRepository;
    private final QnAPostTagMapRepository qnAPostTagMapRepository;

    // 질문게시판) 내가 쓴 게시글 조회
    @Transactional(readOnly = true)
    public List<QnAMyPostResponseDto> getMyPosts(String email) {
        Member member = qnAMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return qnAPostRepository.findByWriter(member).stream()
                .filter(post -> post.getStatus() == Status.ACTIVE)
                .map(post -> {
                    List<String> tags = qnAPostTagMapRepository.findByQnaPost(post).stream()
                            .map(mapping -> mapping.getTag().getTagName())
                            .toList();
                    return QnAMyPostResponseDto.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .createdAt(post.getCreatedAt())
                            .tags(tags)
                            .type(post.getType())
                            .build();
                }).toList();
    }


    // 질문게시판) 내가 쓴 댓글 조회
    @Transactional(readOnly = true)
    public List<QnAMyCommentResponseDto> getMyComments(String email) {
        Member member = qnAMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return qnACommentRepository.findByWriter(member).stream()
                .map(comment -> QnAMyCommentResponseDto.builder()
                        .commentId(comment.getId())
                        .postId(comment.getQnAPost().getId())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .type(comment.getQnAPost().getType())
                        .build())
                .toList();
    }


}
