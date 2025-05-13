package org.example.tackit.domain.QnA_board.QnA_post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.tackit.domain.entity.QnAPost;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class QnAPostResponseDto {
    private final String writer;
    private final String title;
    private final String content;
    private final String tag;
    private final LocalDateTime createdAt;

    public QnAPostResponseDto(QnAPost post) {
        this.writer = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tag = post.getTag();
        this.createdAt = post.getCreatedAt();
    }
}
