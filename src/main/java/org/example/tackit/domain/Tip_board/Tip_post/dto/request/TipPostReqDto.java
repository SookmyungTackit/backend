package org.example.tackit.domain.Tip_board.Tip_post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipPostReqDto {
    private String title;
    private String content;
    private List<Long> tagIds;
}
