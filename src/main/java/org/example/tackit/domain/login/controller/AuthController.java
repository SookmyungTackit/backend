package org.example.tackit.domain.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.login.dto.SignInDto;
import org.example.tackit.domain.login.dto.SignUpDto;
import org.example.tackit.domain.login.dto.TokenDto;
import org.example.tackit.domain.login.service.AuthService;
import org.example.tackit.domain.login.service.CheckService;
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

    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@RequestBody SignUpDto signUpDto) {
        authService.signup(signUpDto);
        return ResponseEntity.ok().body(Map.of("status", "OK", "message", "회원가입 성공했습니다."));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto) {
        TokenDto tokenDto = authService.signIn(signInDto);
        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean isDuplicated = checkService.isEmailDuplicated(email);
        if (isDuplicated) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 이메일입니다.");
        }
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
        boolean isDuplicated = checkService.isNicknameDuplicated(nickname);
        if (isDuplicated) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 닉네임입니다.");
        }
        return ResponseEntity.ok("사용 가능한 닉네임입니다.");
    }

    // 만료된 Access Token 대신 유효한 Refresh Token으로 새로운 Access Token을 발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissueToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        TokenDto tokenDto = authService.reissue(bearerToken);
        return ResponseEntity.ok(tokenDto);
    }
}

