package org.example.tackit.domain.Free_board.Free_post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreePostReqDto {
    private String title;
    private String content;
    private List<Long> tagIds;
}
