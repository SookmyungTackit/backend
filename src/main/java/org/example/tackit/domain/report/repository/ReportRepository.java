package org.example.tackit.domain.report.repository;

import org.example.tackit.domain.entity.Report;
import org.example.tackit.domain.entity.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    // 필요하다면 신고 중복 방지를 위한 조회 메서드도 추가 가능
    boolean existsByReporterIdAndTargetIdAndTargetType(Long reporterId, Long targetId, TargetType targetType);
}

