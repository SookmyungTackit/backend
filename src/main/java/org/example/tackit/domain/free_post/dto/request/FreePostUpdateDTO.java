package org.example.tackit.domain.free_post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

@Getter
@NoArgsConstructor
public class FreePostUpdateDTO {
    private String title;
    private String content;
    private String tag;
}
