package org.example.tackit.domain.free_post.dto.response;

import lombok.Getter;
import org.example.tackit.domain.entity.FreePost;

import java.time.LocalDateTime;

@Getter
public class FreePostDTO {
    private final String writer;
    private final String title;
    private final String content;
    private final String tag;
    private final LocalDateTime created_at;

    public FreePostDTO(FreePost post) {
        this.writer = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tag = post.getTag();
        this.created_at = post.getCreatedAt();

    }

}
