package org.example.tackit.domain.QnA_post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QnAPostRequestDto {
    private String title;
    private String content;
    private String nickname;
    private String tag;
}
