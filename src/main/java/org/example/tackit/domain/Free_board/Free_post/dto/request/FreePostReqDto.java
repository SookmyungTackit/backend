package org.example.tackit.domain.Free_board.Free_post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FreePostReqDto {
    private String title;
    private String content;
    private List<Long> tagIds;
    private MultipartFile image;

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
