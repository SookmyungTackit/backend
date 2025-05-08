package org.example.tackit.domain.tip.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TipPostUpdateDTO {
    private String title;
    private String content;
}
