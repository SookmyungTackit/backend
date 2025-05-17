package org.example.tackit.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.FreeScrap;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FreeScrapResponse {
    private Long freeId;
    private String title;
    private String contentPreview;
    private String authorName;
    private LocalDateTime createdAt;

    public static FreeScrapResponse from(FreeScrap scrap) {
        FreePost post = scrap.getFreePost();
        return new FreeScrapResponse(
                post.getId(),
                post.getTitle(),
                post.getContent().length() > 100
                ? post.getContent().substring(0, 100) + "..." : post.getContent(),
                post.getWriter().getNickname(),
                post.getCreatedAt()
        );
    }
}
