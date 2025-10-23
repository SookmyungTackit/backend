package org.example.tackit.domain.report.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TargetType;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReportListDto {
    private Long reportId;
    private TargetType targetType;
    private String title;
    private Status status;
    private int reportCount;
    // 가장 최근 신고 일자
    private LocalDateTime lastReportedAt;
}
