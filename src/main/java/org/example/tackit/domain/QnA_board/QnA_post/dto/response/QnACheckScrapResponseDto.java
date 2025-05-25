package org.example.tackit.domain.QnA_board.QnA_post.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.domain.entity.QnAPost;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class QnACheckScrapResponseDto {
    private Long postId;
    private String title;
    private String writer;
    private LocalDateTime createdAt;
    private Post type;

    public static QnACheckScrapResponseDto fromEntity(QnAPost post, Post type) {
        return QnACheckScrapResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .writer(post.getWriter().getNickname())
                .createdAt(post.getCreatedAt())
                .type(type)
                .build();
    }

}
