package org.example.tackit.domain.logout.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tackit.config.Redis.RedisUtil;
import org.example.tackit.config.jwt.TokenProvider;
import org.example.tackit.domain.login.repository.UserRepository;
import org.example.tackit.domain.logout.repository.LogoutRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService {
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    public void logout(String bearerToken) {
        String email = tokenProvider.getEmailFromToken(bearerToken);
        long expiration = tokenProvider.getExpiration(bearerToken); // 남은 만료 시간(ms)

        // 1. RefreshToken 제거
        redisUtil.delete(email);

        // 2. AccessToken 블랙리스트 등록
        redisUtil.setBlackList(bearerToken, "logout", expiration);

        log.info("로그아웃 성공: {}", email);
    }
}
