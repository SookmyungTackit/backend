package org.example.tackit.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.domain.entity.TipPost;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipMyPostResponseDto {
    private Long id;
    private String writer;
    private String profileImageUrl;
    private String title;
    private String content;
    private List<String> tags;
    private Post type;
    private LocalDateTime createdAt;
    private String imageUrl;

    public static TipMyPostResponseDto from(TipPost post, List<String> tags) {
        return TipMyPostResponseDto.builder()
                .id(post.getId())
                .writer(post.getWriter().getNickname())
                .profileImageUrl(post.getWriter().getProfileImageUrl())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(tags)
                .type(post.getType())
                .createdAt(post.getCreatedAt())
                .imageUrl(post.getImages().isEmpty() ? null : post.getImages().get(0).getImageUrl()) // ✅ 첫 번째 이미지
                .build();
    }
}
