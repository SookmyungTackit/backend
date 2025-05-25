package org.example.tackit.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreeMyCommentResponseDto {
    private Long commentId;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
}
