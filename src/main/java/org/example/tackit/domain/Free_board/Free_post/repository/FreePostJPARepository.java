package org.example.tackit.domain.Free_board.Free_post.repository;

import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface FreePostJPARepository extends JpaRepository<FreePost, Long> {
    Page<FreePost> findByWriterAndStatus(Member member, Status status, Pageable pageable);

    Page<FreePost> findByOrganizationAndStatus(String organization, Status status, Pageable pageable);

    long countByStatus(Status status);

    // 인기 3개
    List<FreePost> findTop3ByStatusOrderByViewCountDescScrapCountDesc(Status status);

    // 통합 인기 3개
    List<FreePost> findTop3ByStatusAndCreatedAtBetweenOrderByViewCountDescScrapCountDesc(
            Status status, LocalDateTime startOfWeek, LocalDateTime now);
}
