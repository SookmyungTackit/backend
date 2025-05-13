package org.example.tackit.domain.QnA_board.QnA_tag.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_tag.dto.response.QnATagResponseDto;
import org.example.tackit.domain.QnA_board.QnA_tag.repository.QnATagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnATagService {
    private final QnATagRepository qnATagRepository;

    public List<QnATagResponseDto> getAllTags() {
        return qnATagRepository.findAll().stream()
                .map(tag -> QnATagResponseDto.builder()
                        .id(tag.getId())
                        .tagName(tag.getTagName())
                        .build())
                .toList();
    }
}
