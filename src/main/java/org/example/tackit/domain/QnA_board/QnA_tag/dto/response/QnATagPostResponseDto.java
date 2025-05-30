package org.example.tackit.domain.QnA_board.QnA_tag.dto.response;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QnATagPostResponseDto{
    private final Long postId;
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private List<String> tags;

    public QnATagPostResponseDto(Long postId, String writer, String title, String content, LocalDateTime createdAt, List<String> tags) {
        this.postId = postId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.tags = tags;
    }
}
