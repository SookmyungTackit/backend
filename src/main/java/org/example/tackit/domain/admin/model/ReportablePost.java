package org.example.tackit.domain.admin.model;

import org.example.tackit.domain.entity.Member;

import java.time.LocalDateTime;

public interface ReportablePost {
    Long getId();
    Member getWriter();
    String getTitle();
    String getContent();
    String getOrganization();
    LocalDateTime getCreatedAt();
    int getReportCount();


    void activate();

}
