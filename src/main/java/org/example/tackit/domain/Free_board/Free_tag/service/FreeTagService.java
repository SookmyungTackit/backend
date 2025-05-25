package org.example.tackit.domain.Free_board.Free_tag.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_post.service.FreePostTagService;
import org.example.tackit.domain.Free_board.Free_tag.dto.response.FreeTagPostResponseDto;
import org.example.tackit.domain.Free_board.Free_tag.dto.response.FreeTagResponseDto;
import org.example.tackit.domain.Free_board.Free_tag.repository.FreePostTagMapRepository;
import org.example.tackit.domain.Free_board.Free_tag.repository.FreeTagRepository;
import org.example.tackit.domain.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeTagService {
    private final FreeTagRepository freeTagRepository;
    private final FreePostTagMapRepository freePostTagMapRepository;
    private final FreePostTagService freePostTagService;

    // 모든 태그 목록 가져오기
    public List<FreeTagResponseDto> getAllTags() {
        return freeTagRepository.findAll().stream()
                .map(tag -> FreeTagResponseDto.builder()
                        .id(tag.getId())
                        .tagName(tag.getTagName())
                        .build())
                .toList();
    }

    // 태그별 게시물 불러오기
    @Transactional(readOnly = true)
    public List<FreeTagPostResponseDto> getPostsByTag(Long tagId) {
        FreeTag tag = freeTagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("태그가 존재하지 않습니다."));

        List<FreePost> posts = freePostTagMapRepository.findByTag(tag).stream()
                .map(FreeTagMap::getFreePost)
                .filter(post -> post.getStatus() == Status.ACTIVE) // 상태 체크
                .distinct() // 중복 제거
                .toList();

        return posts.stream()
                .map(post -> {
                    List<String> tagNames = freePostTagService.getTagNamesByPost(post);
                    return FreeTagPostResponseDto.builder()
                            .writer(post.getWriter().getNickname())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .createdAt(post.getCreatedAt())
                            .tags(tagNames)
                            .build();
                })
                .toList();
    }
}
