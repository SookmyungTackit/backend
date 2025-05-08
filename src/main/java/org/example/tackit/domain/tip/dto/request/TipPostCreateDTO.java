package org.example.tackit.domain.tip.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TipPostCreateDTO {
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
}
