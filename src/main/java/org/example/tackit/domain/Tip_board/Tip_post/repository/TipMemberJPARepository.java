package org.example.tackit.domain.Tip_board.Tip_post.repository;

import org.example.tackit.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipMemberJPARepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
