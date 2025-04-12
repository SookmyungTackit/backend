package org.example.tackit.domain.login.repository;

import org.example.tackit.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmail(String email); //그 유저 실제 정보 확인
    boolean existsByEmail(String email); //있는지 없는지
    boolean existsByNickname(String nickname); //닉네임 중복 확인
}

