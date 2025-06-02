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
import org.example.tackit.global.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class QnATagService {
    private final QnATagRepository qnATagRepository;

    // 모든 태그 목록 가져오기
    public List<QnATagResponseDto> getAllTags() {
        return qnATagRepository.findAll().stream()
                .map(tag -> QnATagResponseDto.builder()
                        .id(tag.getId())
                        .tagName(tag.getTagName())
                        .build())
                .toList();
    }

    // 태그별 게시물 불러오기
    @Transactional(readOnly = true)
    public PageResponseDTO<QnATagPostResponseDto> getPostsByTag(Long tagId, String organization, Pageable pageable) {
        Page<QnATagPostResponseDto> page = qnATagRepository.findPostsByTagId(tagId, organization, pageable);
        return PageResponseDTO.from(page, Function.identity()); // 이미 DTO라 변환이 필요 없음
    }

}
