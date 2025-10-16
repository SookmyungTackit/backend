package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT u FROM Member u WHERE u.email <> 'admin' ORDER BY " +
            "CASE WHEN u.status = 0 THEN 0 ELSE 1 END")
    List<Member> findAllOrderByStatus();

    Optional<Member> findByEmail(String email);

    // 총 가입자 수
    @Query("SELECT COUNT(m) FROM Member m WHERE m.email <> 'admin'")
    Long countAll();

    // 이번 달/주 가입자 수
    @Query("SELECT COUNT(m) FROM Member m WHERE m.createdAt >= :date AND m.email <> 'admin'")
    Long countJoinedAfter(@Param("date")LocalDateTime date);

    // 탈퇴 회원 통계
    List<Member> findByStatus(Status status);
    Long countByStatus(Status status);

}
