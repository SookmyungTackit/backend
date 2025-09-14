package org.example.tackit.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.domain.entity.TipPost;
import org.example.tackit.domain.entity.TipScrap;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TipScrapResponse {
    private Long tipId;
    private String title;
    private String contentPreview;
    private String authorName;
    private LocalDateTime createdAt;
    private Post type;

    public static TipScrapResponse from(TipScrap scrap, Post type) {
        TipPost post = scrap.getTipPost();
        return new TipScrapResponse(
                post.getId(),
                post.getTitle(),
                post.getContent().length() > 100
                ? post.getContent().substring(0, 100) + "..." : post.getContent(),
                post.getWriter().getNickname(),
                post.getCreatedAt(),
                post.getType()
        );
    }
}
