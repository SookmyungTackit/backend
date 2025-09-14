package org.example.tackit.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.domain.entity.QnAPost;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnAMyPostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private List<String> tags;
    private Post type;

    public static QnAMyPostResponseDto fromEntity(QnAPost post, List<String> tags) {
        return QnAMyPostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .tags(tags)
                .type(post.getType())
                .build();
    }
}
