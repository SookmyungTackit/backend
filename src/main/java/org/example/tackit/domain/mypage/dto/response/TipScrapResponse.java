package org.example.tackit.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class TipScrapResponse {
    private Long tipId;
    private String title;
    private String contentPreview;
    private String authorName;
    private LocalDateTime createdAt;
    private Post type;
    private List<String> tags;

    public static TipScrapResponse from(TipScrap scrap, List<String> tags) {
        TipPost post = scrap.getTipPost();

        return TipScrapResponse.builder()
                .tipId(post.getId())
                .title(post.getTitle())
                .contentPreview(post.getContent().length() > 100
                        ? post.getContent().substring(0, 100) + "..."
                        : post.getContent())
                .authorName(post.getWriter().getNickname())
                .createdAt(post.getCreatedAt())
                .type(scrap.getType())
                .tags(tags)
                .build();
    }
}
