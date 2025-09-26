package org.example.tackit.domain.Free_board.Free_post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.FreePost;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class FreePostRespDto {
    private long id;
    private final String writer;
    private final String title;
    private final String content;
    private final List<String> tags;
    private final LocalDateTime createdAt;
    private String imageUrl;
}
