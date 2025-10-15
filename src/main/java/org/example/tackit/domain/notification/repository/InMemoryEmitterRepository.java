package org.example.tackit.domain.notification.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryEmitterRepository implements EmitterRepository {
    // 동시성 고려하여 ConcurrentHashMap 사용
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(Long userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
        return null;
    }

    @Override
    public Optional<SseEmitter> findById(Long userId) {
        return Optional.ofNullable(emitters.get(userId));
    }

    @Override
    public Map<Long, SseEmitter> findAll() {
        return new ConcurrentHashMap<>(emitters);
    }

    @Override
    public void deleteById(Long userId) {
        emitters.remove(userId);
    }
}
