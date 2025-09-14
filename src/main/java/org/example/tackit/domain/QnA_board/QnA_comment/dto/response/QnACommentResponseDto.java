package org.example.tackit.domain.QnA_board.QnA_comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.tackit.domain.entity.QnAComment;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class QnACommentResponseDto {
    private final long id;
    private final String writer;
    private final String content;
    private final LocalDateTime createdAt;

    public QnACommentResponseDto(QnAComment comment) {
        this.id = comment.getId();
        this.writer = comment.getWriter().getNickname();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
