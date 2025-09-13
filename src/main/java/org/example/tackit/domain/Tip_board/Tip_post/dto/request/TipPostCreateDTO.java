package org.example.tackit.domain.Tip_board.Tip_post.dto.request;

import lombok.Getter;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipPost;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TipPostCreateDTO {
    private String title;
    private String content;
    private List<Long> tagIds;

    public TipPost toEntity(Member writer, String org) {
        return TipPost.builder()
                .title(this.title)
                .content(this.content)
                .writer(writer)
                .organization(org)
                .type(Post.Tip)
                .createdAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
    }
}
