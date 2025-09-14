package org.example.tackit.domain.Free_board.Free_post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateFreeReqDto {
    private String title;
    private String content;
    private List<Long> tagIds;
}
