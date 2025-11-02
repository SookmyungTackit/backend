package org.example.tackit.domain.auth.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.auth.login.dto.SignInDto;
import org.example.tackit.domain.auth.login.dto.SignUpDto;
import org.example.tackit.domain.auth.login.dto.TokenDto;
import org.example.tackit.domain.auth.login.service.AuthService;
import org.example.tackit.domain.auth.login.service.CheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CheckService checkService;

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpDto signUpDto) {
        authService.signup(signUpDto);
        return ResponseEntity.ok().body(Map.of("status", "OK", "message", "회원가입 성공했습니다."));
    }

    // 로그인
    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto) {
        TokenDto tokenDto = authService.signIn(signInDto);
        return ResponseEntity.ok(tokenDto);
    }

    // 토큰 재발급
    // 만료된 Access Token 대신 유효한 Refresh Token으로 새로운 Access Token을 발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissueToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        TokenDto tokenDto = authService.reissue(bearerToken);
        return ResponseEntity.ok(tokenDto);
    }
}

