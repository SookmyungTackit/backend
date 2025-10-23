package org.example.tackit.domain.report.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TargetType;

import java.util.List;

@Getter
@Builder
public class ReportContentDetailDto {
    private Long targetId;
    private TargetType targetType;
    private String postType;
    private String contentTitle;
    private String contentWriter;
    private Status status;

    private List<ReportLogDto> reportLogs;

}
