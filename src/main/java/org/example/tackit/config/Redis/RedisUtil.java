package org.example.tackit.config.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    // 저장
    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 조회 (refreshToken 비교용)
    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 삭제 (로그아웃 시 사용 가능)
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
