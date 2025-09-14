package org.example.tackit.domain.Tip_board.Tip_post.repository;

import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.TipPost;
import org.example.tackit.domain.entity.TipReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipPostReportRepository extends JpaRepository<TipReport, Long> {
    boolean existsByMemberAndTipPost(Member member, TipPost tipPost);
}
