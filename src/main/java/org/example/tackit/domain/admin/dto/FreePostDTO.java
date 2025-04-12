package org.example.tackit.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FreePostDTO {
    private String title;
    private String nickname;
    private LocalDateTime createdAt;
    private int reportCount;
}
