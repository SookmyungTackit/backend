package org.example.tackit.domain.QnA_board.QnA_tag.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_post.dto.response.QnAPostResponseDto;
import org.example.tackit.domain.QnA_board.QnA_post.service.QnAPostTagService;
import org.example.tackit.domain.QnA_board.QnA_tag.dto.response.QnATagPostResponseDto;
import org.example.tackit.domain.QnA_board.QnA_tag.dto.response.QnATagResponseDto;
import org.example.tackit.domain.QnA_board.QnA_tag.repository.QnAPostTagMapRepository;
import org.example.tackit.domain.QnA_board.QnA_tag.repository.QnATagRepository;
import org.example.tackit.domain.entity.QnAPost;
import org.example.tackit.domain.entity.QnATag;
import org.example.tackit.domain.entity.QnATagMap;
import org.example.tackit.domain.entity.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnATagService {
    private final QnATagRepository qnATagRepository;
    private final QnAPostTagMapRepository qnAPostTagMapRepository;
    private final QnAPostTagService qnAPostTagService;

    // 모든 태그 목록 가져오기
    public List<QnATagResponseDto> getAllTags() {
        return qnATagRepository.findAll().stream()
                .map(tag -> QnATagResponseDto.builder()
                        .id(tag.getId())
                        .tagName(tag.getTagName())
                        .build())
                .toList();
    }

//    // 태그별 게시물 불러오기
//    @Transactional(readOnly = true)
//    public List<QnATagPostResponseDto> getPostsByTag(Long tagId) {
//        QnATag tag = qnATagRepository.findById(tagId)
//                .orElseThrow(() -> new IllegalArgumentException("태그가 존재하지 않습니다."));
//
//        List<QnAPost> posts = qnAPostTagMapRepository.findByTag(tag).stream()
//                .map(QnATagMap::getQnaPost)
//                .filter(post -> post.getStatus() == Status.ACTIVE) // 상태 체크
//                .distinct() // 중복 제거
//                .toList();
//
//        return posts.stream()
//                .map(post -> {
//                    List<String> tagNames = qnAPostTagService.getTagNamesByPost(post);
//                    return QnATagPostResponseDto.builder()
//                            .writer(post.getWriter().getNickname())
//                            .title(post.getTitle())
//                            .content(post.getContent())
//                            .createdAt(post.getCreatedAt())
//                            .tags(tagNames)
//                            .build();
//                })
//                .toList();
//    }
}
