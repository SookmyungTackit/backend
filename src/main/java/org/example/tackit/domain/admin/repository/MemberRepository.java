package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT u FROM Member u ORDER BY " +
            "CASE WHEN u.status = 0 THEN 0 ELSE 1 END")
    List<Member> findAllOrderByStatus();
}
