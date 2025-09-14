package org.example.tackit.domain.QnA_board.QnA_post.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.domain.entity.QnAPost;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class QnACheckScrapResponseDto {
    private final Long postId;
    private final String title;
    private final String writer;
    private final String contentPreview;
    private final List<String> tags;
    private final LocalDateTime createdAt;
    private final Post type;

    public static QnACheckScrapResponseDto fromEntity(QnAPost post, Post type, List<String> tagNames) {
        String content = post.getContent();
        if (content.length() > 100) {
            content = content.substring(0, 100) + "...";
        }

        return QnACheckScrapResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .writer(post.getWriter().getNickname())
                .contentPreview(content)
                .tags(tagNames)
                .createdAt(post.getCreatedAt())
                .type(type)
                .build();
    }

}
