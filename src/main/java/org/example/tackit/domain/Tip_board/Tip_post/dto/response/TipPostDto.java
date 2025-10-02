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
public class TipPostDto {
    private Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final List<String> tags;
    private final String imageUrl;

    public TipPostDto(TipPost post, List<String> tags) {
        this.id = post.getId();
        this.writer = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.tags = tags;
        this.imageUrl = post.getImages().isEmpty() ? null : post.getImages().get(0).getImageUrl();
    }

    // 태그 X ) 기존 fromEntity
    public static TipPostDto fromEntity(TipPost post) {
        return new TipPostDto(post, List.of()); // 태그 없는 경우 빈 리스트
    }

    // 태그 O ) 정적 팩토리 메서드
    public static TipPostDto fromEntity(TipPost post, List<String> tags) {
        return new TipPostDto(post, tags);
    }

}
