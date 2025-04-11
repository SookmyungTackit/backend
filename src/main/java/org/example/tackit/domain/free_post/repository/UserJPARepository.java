package org.example.tackit.domain.free_post.repository;

import org.example.tackit.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<Member, Integer> {
    Member findTopByOrderByIdAsc();
    Optional<Member> findByNickname(String nickname);
}
