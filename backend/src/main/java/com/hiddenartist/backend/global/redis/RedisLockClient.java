package com.hiddenartist.backend.global.redis;

import com.hiddenartist.backend.domain.mentoring.persistence.LockApplicationTime;
import com.hiddenartist.backend.global.exception.type.MentoringException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.type.EntityToken;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisLockClient {

  private final LockApplicationTimeClient lockApplicationTimeClient;

  public void reservationApplicationTime(LockApplicationTime lockApplicationTime) {
    String tokenValue = EntityToken.MENTORING.extractToken(lockApplicationTime.getToken());
    String key = LockApplicationTimeClient.generateKey(tokenValue, lockApplicationTime.getApplicationTime());
    LockApplicationTime reservedApplicationTime = lockApplicationTimeClient.findBy(key);
    if (Objects.nonNull(reservedApplicationTime) &&
        isNotReservationTimeOwner(reservedApplicationTime.getEmail(), lockApplicationTime.getEmail())) {
      throw new MentoringException(ServiceErrorCode.TIME_ALREADY_LOCK);
    }
    lockApplicationTimeClient.save(lockApplicationTime);
  }

  private boolean isNotReservationTimeOwner(String reservedEmail, String targetEmail) {
    return !reservedEmail.equals(targetEmail);
  }

}