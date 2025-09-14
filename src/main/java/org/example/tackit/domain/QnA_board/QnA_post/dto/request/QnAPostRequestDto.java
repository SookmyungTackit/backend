package org.example.tackit.domain.QnA_board.QnA_post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class QnAPostRequestDto {
    private String title;
    private String content;
    private List<Long> tagIds;
}
