package org.example.tackit.domain.mypage.repository;

import org.example.tackit.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface MemberDetailRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); //email로 사용자 정보를 가져옴
}
