package org.example.tackit.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.FreeScrap;
import org.example.tackit.domain.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FreeScrapResponse {
    private Long freeId;
    private String title;
    private String contentPreview;
    private String writer;
    private String profileImageUrl;
    private LocalDateTime createdAt;
    private Post type;
    private List<String> tags;
    private String imageUrl;

    public static FreeScrapResponse from(FreeScrap scrap, List<String> tags) {
        FreePost post = scrap.getFreePost();

        return FreeScrapResponse.builder()
                .freeId(post.getId())
                .title(post.getTitle())
                .contentPreview(post.getContent().length() > 100
                        ? post.getContent().substring(0, 100) + "..."
                        : post.getContent())
                .writer(post.getWriter().getNickname())
                .profileImageUrl(post.getWriter().getProfileImageUrl())
                .createdAt(post.getCreatedAt())
                .type(scrap.getType())
                .tags(tags)
                .imageUrl(post.getImages().isEmpty() ? null : post.getImages().get(0).getImageUrl())
                .build();


    }
}
