package org.example.tackit.domain.Free_board.Free_tag.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.FreePost;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class FreeTagPostResponseDto {
    private final String writer;
    private final String title;
    private final String content;
    private final List<String> tags;
    private final LocalDateTime createdAt;

    public FreeTagPostResponseDto(FreePost post, List<String> tags) {
        this.writer = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tags = tags;
        this.createdAt = post.getCreatedAt();
    }
}
