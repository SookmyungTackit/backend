package org.example.tackit.domain.QnA_board.QnA_post.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_post.dto.response.QnACheckScrapResponseDto;
import org.example.tackit.domain.QnA_board.QnA_post.dto.response.QnAScrapResponseDto;
import org.example.tackit.domain.QnA_board.QnA_post.repository.QnAMemberRepository;
import org.example.tackit.domain.QnA_board.QnA_post.repository.QnAPostRepository;
import org.example.tackit.domain.QnA_board.QnA_post.repository.QnAScrapRepository;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.domain.entity.QnAPost;
import org.example.tackit.domain.entity.QnAScrap;
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnAScrapService {
    private final QnAScrapRepository qnAScrapRepository;
    private final QnAPostRepository qnAPostRepository;
    private final QnAMemberRepository qnAMemberRepository;


    // 스크랩한적 있으면 스크랩 취소, 없으면 저장
    @Transactional
    public QnAScrapResponseDto toggleScrap(long postId, String email, String userOrg){
        Member user = qnAMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        QnAPost post = qnAPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 작성글의 소속과 찜하는 사람 간 소속(organization)이 일치하지 않으면 예외
        if (!post.getWriter().getOrganization().equals(userOrg)) {
            throw new AccessDeniedException("해당 조직 게시글이 아닙니다.");
        }

        Optional<QnAScrap> existing = qnAScrapRepository.findByUserAndQnaPost(user, post);

        if (existing.isPresent()) {
            qnAScrapRepository.delete(existing.get());
            return new QnAScrapResponseDto(false, null);
        } else {
            QnAScrap scrap = QnAScrap.builder()
                    .user(user)
                    .qnaPost(post)
                    .savedAt(LocalDateTime.now())
                    .build();
            qnAScrapRepository.save(scrap);
            return new QnAScrapResponseDto(true, scrap.getSavedAt());
        }
    }

    // 내가 찜한 글 불러오기
    // 찜은 사용자 개인이 찜한 글 + 찜할때 이미 소속 검사함 -> 소속확인 필요 x
    @Transactional(readOnly = true)
    public PageResponseDTO<QnACheckScrapResponseDto> getMyQnAScraps(String email, Pageable pageable) {
        Member user = qnAMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Page<QnAScrap> page = qnAScrapRepository.findByUserAndType(user, Post.QnA, pageable);

        return PageResponseDTO.from(page, scrap ->
                QnACheckScrapResponseDto.fromEntity(scrap.getQnaPost(), scrap.getType())
        );
    }



}
