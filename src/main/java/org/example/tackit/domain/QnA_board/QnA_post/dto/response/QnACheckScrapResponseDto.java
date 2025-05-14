package org.example.tackit.domain.QnA_board.QnA_post.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.Post;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class QnACheckScrapResponseDto {
    private Long postId;
    private String title;
    private String writer;
    private LocalDateTime createdAt;
    private Post type;
}
