package org.example.tackit.domain.Tip_board.Tip_post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TipPostUpdateDto {
    private String title;
    private String content;
    private List<Long> tagIds;
}
