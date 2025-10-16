package org.example.tackit.domain.notification.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;

// 추후 Redis 기반으로 바꾸는 것도 고려하여 인터페이스로 구현
@Repository
public interface EmitterRepository {

    // Emitter 저장
    SseEmitter save(Long userId, SseEmitter emitter);

    // 회원 ID로 Emitter 조회
    Optional<SseEmitter> findById(Long userId);

    // 모든 Emitter 조회
    Map<Long, SseEmitter> findAll();

    // 연결 종료 시 Emitter 제거
    void deleteById(Long userId);

}
