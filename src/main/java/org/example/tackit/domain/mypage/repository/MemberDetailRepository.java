package org.example.tackit.domain.mypage.repository;

import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberDetailRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); //email로 사용자 정보를 가져옴

    // 1년마다 뉴비 -> 시니어 자동 갱신
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Member m " +
            "SET m.role = :newRole " +
            "WHERE m.role = :oldRole " +
            "AND m.joinedYear <= :thresholdYear")
    int bulkUpdateRole(@Param("oldRole") Role oldRole,
                       @Param("newRole") Role newRole,
                       @Param("thresholdYear") int thresholdYear);
    // 닉네임 중복확인
    boolean existsByNickname(String nickname);
}
