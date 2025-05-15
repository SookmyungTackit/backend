package org.example.tackit.domain.tip.dto.response;

import lombok.Getter;
import org.example.tackit.domain.entity.TipPost;

import java.time.LocalDateTime;

@Getter
public class TipPostDTO {
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime created_at;

    public TipPostDTO(TipPost post) {
        this.writer = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.created_at = post.getCreatedAt();
    }

    // 정적 팩토리 메서드
    public static TipPostDTO fromEntity(TipPost post) {
        return new TipPostDTO(post);
    }

}
