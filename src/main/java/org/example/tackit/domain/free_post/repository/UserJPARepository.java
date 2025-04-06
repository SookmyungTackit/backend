package org.example.tackit.domain.free_post.repository;

import org.example.tackit.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {
    User findTopByOrderByIdAsc();
    Optional<User> findByNickname(String nickname);
}
