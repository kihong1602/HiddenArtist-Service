package com.hiddenartist.backend.global.redis;

import com.hiddenartist.backend.global.exception.type.EntityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.jwt.RefreshToken;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("RefreshTokenClient")
@RequiredArgsConstructor
@Transactional
public class RefreshTokenClient implements RedisClient<RefreshToken> {

  private static final long TTL = 7L;
  private final RedisTemplate<String, RefreshToken> redis;

  @Override
  public void save(RefreshToken value) {
    String key = REFRESH_TOKEN_PREFIX + value.getEmail();
    redis.opsForValue().set(key, value, Duration.ofDays(TTL));
  }

  @Override
  public RefreshToken findBy(String key) {
    RefreshToken value = redis.opsForValue().get(REFRESH_TOKEN_PREFIX + key);
    return Optional.ofNullable(value).orElseThrow(() -> new EntityException(ServiceErrorCode.REDIS_VALUE_NOT_FOUND));
  }

  @Override
  public void deleteBy(String key) {
    redis.delete(REFRESH_TOKEN_PREFIX + key);
  }
}
