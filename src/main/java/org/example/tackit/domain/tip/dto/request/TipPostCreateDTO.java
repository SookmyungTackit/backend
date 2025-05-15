package org.example.tackit.domain.tip.dto.request;

import lombok.Getter;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipPost;

import java.time.LocalDateTime;

@Getter
public class TipPostCreateDTO {
    private String title;
    private String content;

    public TipPost toEntity(Member writer, String org) {
        return TipPost.builder()
                .title(this.title)
                .content(this.content)
                .writer(writer)
                .organization(org)
                .createdAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
    }
}
