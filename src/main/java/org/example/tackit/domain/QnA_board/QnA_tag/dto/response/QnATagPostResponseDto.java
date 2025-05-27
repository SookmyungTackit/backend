package org.example.tackit.domain.QnA_board.QnA_tag.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.tackit.domain.entity.QnAPost;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QnATagPostResponseDto{
    private String title;
    private String content;
    private List<Long> tagIds;

//    @QueryProjection // querydsl에서 dto 반환
//    public QnATagPostResponseDto(Long postId, String writer, String title, String content, LocalDateTime createdAt) {
//        this.postId = postId;
//        this.writer = writer;
//        this.title = title;
//        this.content = content;
//        this.createdAt = createdAt;
//    }
}
