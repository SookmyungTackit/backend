package org.example.tackit.domain.auth.logout.repository;

import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogoutRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndStatus(String email, Status status);
    void deleteByEmail(String email); // 하드 삭제용
}
