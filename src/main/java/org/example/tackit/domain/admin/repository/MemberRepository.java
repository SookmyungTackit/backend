package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT u FROM Member u ORDER BY " +
            "CASE WHEN u.status = 0 THEN 0 ELSE 1 END")
    List<Member> findAllOrderByStatus();

    Optional<Member> findByEmail(String email);
    Member findTopByOrderByIdAsc();

    // 총 가입자 수
    @Query("SELECT COUNT(m) FROM Member m")
    Long countAll();

    // 이번 달/주 가입자 수
    @Query("SELECT COUNT(m) FROM Member m WHERE m.createdAt >= :date")
    Long countJoinedAfter(@Param("date")LocalDateTime date);

}
