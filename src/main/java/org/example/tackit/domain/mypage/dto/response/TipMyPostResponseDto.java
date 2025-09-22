package org.example.tackit.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.tackit.domain.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipMyPostResponseDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private List<String> tags;
    private Post type;
    private LocalDateTime createdAt;
}
