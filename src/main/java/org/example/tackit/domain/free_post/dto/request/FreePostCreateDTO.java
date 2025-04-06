package org.example.tackit.domain.free_post.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FreePostCreateDTO {
    private String title;
    private String content;
    private String nickname;
    private String tag;
    private LocalDateTime createdAt;
}
