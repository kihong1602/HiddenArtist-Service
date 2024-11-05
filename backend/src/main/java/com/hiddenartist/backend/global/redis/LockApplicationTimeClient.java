package com.hiddenartist.backend.global.redis;

import com.hiddenartist.backend.domain.mentoring.persistence.LockApplicationTime;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("LockApplicationTimeClient")
@Transactional
@RequiredArgsConstructor
public class LockApplicationTimeClient implements RedisClient<LockApplicationTime> {

  private static final long TTL = 10L;
  private final RedisTemplate<String, LockApplicationTime> redis;

  public static String generateKey(String tokenValue, LocalDateTime applicationTime) {
    return MENTORING_PREFIX + tokenValue + ":" + applicationTime.format(DateTimeFormatter.ofPattern("yyyy_MM")) + ":"
        + applicationTime.format(DateTimeFormatter.ofPattern("dd_HH_mm"));
  }

  @Override
  public void save(LockApplicationTime value) {
    String tokenValue = EntityToken.MENTORING.extractToken(value.getToken());
    String key = generateKey(tokenValue, value.getApplicationTime());
    redis.opsForValue().set(key, value, Duration.ofMinutes(TTL));
  }

  @Override
  public LockApplicationTime findBy(String key) {
    return redis.opsForValue().get(key);
  }

  public List<LockApplicationTime> findByKeyword(String tokenValue, LocalDate selectMonth) {
    String keyword = MENTORING_PREFIX + tokenValue + ":" + selectMonth.format(DateTimeFormatter.ofPattern("yyyy_MM"));
    List<LockApplicationTime> lockApplicationTimes = new ArrayList<>();
    ScanOptions options = ScanOptions.scanOptions().match(keyword + "*").count(100).build();
    try (Cursor<String> scan = redis.scan(options)) {
      while (scan.hasNext()) {
        String key = scan.next();
        Optional.ofNullable(findBy(key)).ifPresent(lockApplicationTimes::add);
      }
    }
    return lockApplicationTimes;
  }

  @Override
  public void deleteBy(String key) {
    redis.delete(key);
  }

}