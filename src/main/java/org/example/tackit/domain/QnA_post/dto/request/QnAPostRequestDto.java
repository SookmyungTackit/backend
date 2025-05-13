package org.example.tackit.domain.QnA_post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QnAPostRequestDto {
    private String title;
    private String content;
    private String tag;
}
