package org.example.tackit.domain.QnA_board.QnA_post.repository;

import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.QnAPost;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QnAPostRepository extends JpaRepository<QnAPost, Long> {
    // 상태로 조회
    Page<QnAPost> findAllByStatusAndWriter_Organization(Status status, String org, Pageable pageable);
    Page<QnAPost> findByWriterAndStatus(Member writer, Status status, Pageable pageable);

    long countByStatus(Status status);

    // 인기 3개
    List<QnAPost> findTop3ByStatusOrderByViewCountDescScrapCountDesc(Status status);

    // 통합 인기 3개
    List<QnAPost> findTop3ByStatusAndCreatedAtBetweenOrderByViewCountDescScrapCountDesc(
            Status status, LocalDateTime startOfWeek, LocalDateTime now);
}
