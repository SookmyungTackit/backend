package org.example.tackit.domain.Tip_board.Tip_post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.TipPost;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class TipPostDTO {
    private Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final List<String> tags;

    public TipPostDTO(TipPost post, List<String> tags) {
        this.id = post.getId();
        this.writer = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.tags = tags;
    }

    // 태그 X ) 기존 fromEntity
    public static TipPostDTO fromEntity(TipPost post) {
        return new TipPostDTO(post, List.of()); // 태그 없는 경우 빈 리스트
    }

    // 태그 O ) 정적 팩토리 메서드
    public static TipPostDTO fromEntity(TipPost post, List<String> tags) {
        return new TipPostDTO(post, tags);
    }

}
