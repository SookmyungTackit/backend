package org.example.tackit.domain.QnA_board.QnA_tag.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.QnAPost;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class QnATagPostResponseDto {
    private final String writer;
    private final String title;
    private final String content;
    private final List<String> tags;
    private final LocalDateTime createdAt;

    public QnATagPostResponseDto(QnAPost post, List<String> tags) {
        this.writer = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tags = tags;
        this.createdAt = post.getCreatedAt();
    }
}
