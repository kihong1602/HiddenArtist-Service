package com.hiddenartist.backend.global.redis;

import static org.assertj.core.api.Assertions.assertThat;

import com.hiddenartist.backend.domain.mentoring.service.MentoringService;
import com.hiddenartist.backend.global.config.AbstractIntegrationTest;
import com.hiddenartist.backend.global.exception.type.MentoringException;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisDistributeLockTest extends AbstractIntegrationTest {

  @Autowired
  private MentoringService mentoringService;

  @Test
  @DisplayName("신청시간 잠금 테스트")
  void applicationTimeLockTest() {
    //given
    ExecutorService executor = Executors.newFixedThreadPool(2);

    String defaultEmail = "test@test.com";
    String competitionEmail = "test2@test.com";
    String token = EntityToken.MENTORING.identifyToken("1");
    LocalDateTime applicationTime = LocalDateTime.of(2024, 10, 24, 15, 30);

    AtomicInteger exceptionCount = new AtomicInteger();
    //when
    CompletableFuture<Void> request1 = CompletableFuture.runAsync(
        () -> {
          try {
            mentoringService.reservationApplicationTime(token, applicationTime, defaultEmail);
          } catch (MentoringException e) {
            exceptionCount.getAndIncrement();
          }
        }, executor);
    CompletableFuture<Void> request2 = CompletableFuture.runAsync(
        () -> {
          try {
            mentoringService.reservationApplicationTime(token, applicationTime, competitionEmail);
          } catch (MentoringException e) {
            exceptionCount.getAndIncrement();
          }
        }, executor);

    CompletableFuture.allOf(request1, request2).join();

    //then
    assertThat(exceptionCount.get()).isEqualTo(1);
  }

}