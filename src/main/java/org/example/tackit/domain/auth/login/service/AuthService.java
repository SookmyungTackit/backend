package org.example.tackit.domain.auth.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tackit.config.Redis.RedisUtil;
import org.example.tackit.config.jwt.TokenProvider;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Role;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.auth.login.dto.SignInDto;
import org.example.tackit.domain.auth.login.dto.SignUpDto;
import org.example.tackit.domain.auth.login.dto.TokenDto;
import org.example.tackit.domain.auth.login.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    @Transactional
    public void signup(SignUpDto signUpDto) {
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        if (userRepository.existsByNickname(signUpDto.getNickname())) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다");
        }

        int currentYear = LocalDate.now().getYear();

        Member member = Member.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .nickname(signUpDto.getNickname())
                .organization(signUpDto.getOrganization())
                .role(signUpDto.getRole())
                .joinedYear(currentYear)
                .status(Status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(member);
    }

    @Transactional
    public TokenDto signIn(SignInDto signInDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword());

        try {
            log.info("로그인 시도: {}", signInDto.getEmail());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            log.info("로그인 성공: {}", authentication.getName());

            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
            redisUtil.save(signInDto.getEmail(), tokenDto.getRefreshToken());
            return tokenDto;
        } catch (Exception e) {
            log.error("로그인 실패", e);
            throw e;
        }
    }

    // Bearer 제거 및 형식 검증
    public String resolveRefreshToken(String refreshToken) {
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new BadCredentialsException("리프레시 토큰이 누락되었거나 올바르지 않습니다.");
        }
        return refreshToken.substring(7);
    }

    // 토큰 재발급 처리
    @Transactional
    public TokenDto reissue(String bearerToken) {
        String refreshToken = resolveRefreshToken(bearerToken);
        return tokenProvider.reissueAccessToken(refreshToken);
    }
}

