package org.example.tackit.domain.QnA_board.QnA_post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.tackit.domain.entity.QnAPost;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class QnAPostResponseDto {
    private final Long postId;
    private final String writer;
    private final String title;
    private final String content;
    private final List<String> tags;
    private final LocalDateTime createdAt;

    public static QnAPostResponseDto fromEntity(QnAPost post, List<String> tagNames) {
        return QnAPostResponseDto.builder()
                .postId(post.getId())
                .writer(post.getWriter().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(tagNames)
                .createdAt(post.getCreatedAt())
                .build();
    }
}
