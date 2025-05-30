package org.example.tackit.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.tackit.domain.admin.model.ReportablePost;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReportedPostDTO {
    private long id;
    private String title;
    private String nickname;
    private String organization;
    private LocalDateTime createdAt;
    private int reportCount;

    public static ReportedPostDTO fromEntity(ReportablePost post){
        return new ReportedPostDTO(
                post.getId(),
                post.getTitle(),
                post.getWriter().getNickname(),
                post.getWriter().getOrganization(),
                post.getCreatedAt(),
                post.getReportCount()
        );
    }
}
