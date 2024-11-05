package com.hiddenartist.backend.global.redis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hiddenartist.backend.domain.mentoring.persistence.LockApplicationTime;
import com.hiddenartist.backend.global.config.AbstractRedisTest;
import com.hiddenartist.backend.global.exception.type.MentoringException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisLockClientTest extends AbstractRedisTest {

  @Autowired
  private RedisLockClient redisLockClient;

  @Autowired
  private LockApplicationTimeClient lockApplicationTimeClient;

  @Test
  @DisplayName("잠금처리된 신청시간 미 존재시 신청시간 잠금처리")
  void applicationTimeLockTest() {
    //given
    String token = EntityToken.MENTORING.identifyToken("1");
    LocalDateTime applicationTime = LocalDateTime.of(2024, 10, 24, 13, 30);
    LockApplicationTime lockApplicationTime = new LockApplicationTime("test@test.com", token, applicationTime);
    String key = LockApplicationTimeClient.generateKey(EntityToken.MENTORING.extractToken(token), applicationTime);

    //when
    redisLockClient.reservationApplicationTime(lockApplicationTime);

    LockApplicationTime result = lockApplicationTimeClient.findBy(key);

    //then
    assertThat(result).isNotNull()
                      .extracting("email", "applicationTime")
                      .containsExactly("test@test.com", applicationTime);
  }

  @Test
  @DisplayName("잠금처리된 신청시간 존재시 예외 발생")
  void applicationTimeAlreadyLockTest() {
    //given
    String token = EntityToken.MENTORING.identifyToken("1");
    LocalDateTime applicationTime = LocalDateTime.of(2024, 10, 24, 13, 30);
    String alreadyReservedEmail = "test@test.com";
    LockApplicationTime alreadyLockApplicationTime = new LockApplicationTime(alreadyReservedEmail, token, applicationTime);

    String reservationEmail = "test2@test.com";
    LockApplicationTime lockApplicationTime = new LockApplicationTime(reservationEmail, token, applicationTime);

    //when
    lockApplicationTimeClient.save(alreadyLockApplicationTime);

    //then
    assertThatThrownBy(() -> redisLockClient.reservationApplicationTime(lockApplicationTime))
        .isInstanceOf(MentoringException.class)
        .hasMessage(ServiceErrorCode.TIME_ALREADY_LOCK.getErrorMessage());
  }

}