package org.example.tackit.domain.QnA_board.QnA_post.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QnAPostRequestDto {
    private String title;
    private String content;
    private List<Long> tagIds;
    private MultipartFile imageUrl;
}
