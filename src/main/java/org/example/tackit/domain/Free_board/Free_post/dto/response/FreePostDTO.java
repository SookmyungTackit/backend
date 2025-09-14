package org.example.tackit.domain.Free_board.Free_post.dto.response;

import lombok.Getter;
import org.example.tackit.domain.entity.FreePost;

import java.time.LocalDateTime;

@Getter
public class FreePostDTO {
    private final String writer;
    private final String title;
    private final String content;
    private final String tag;
    private final LocalDateTime createdAt;

    public FreePostDTO(FreePost post) {
        this.writer = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tag = post.getTag();
        this.createdAt = post.getCreatedAt();

    }

}
