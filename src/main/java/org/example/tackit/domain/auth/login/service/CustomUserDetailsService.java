package org.example.tackit.domain.auth.login.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.auth.login.repository.UserRepository;
import org.example.tackit.domain.entity.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));

        // 상태 확인 추가
        if (member.getStatus() == Status.DELETED) {
            throw new UsernameNotFoundException(email + " -> 탈퇴한 회원입니다.");
        }

        return createUserDetails(member);
    }

    // DB에 Member 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().toString());

        /*
        return new User(
                member.getEmail(), // 사용자 식별자로 이메일을 사용
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
         */
        return new CustomUserDetails(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getOrganization(),
                Collections.singleton(grantedAuthority)
        );
    }
}
