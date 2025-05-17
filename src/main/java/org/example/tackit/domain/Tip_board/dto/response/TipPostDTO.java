package org.example.tackit.domain.Tip_board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.TipPost;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class TipPostDTO {
    private Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public TipPostDTO(TipPost post) {
        this.id = post.getId();
        this.writer = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }

    // 정적 팩토리 메서드
    public static TipPostDTO fromEntity(TipPost post) {
        return new TipPostDTO(post);
    }

}
