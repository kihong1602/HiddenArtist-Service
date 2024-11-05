package com.hiddenartist.backend.global.config;

import com.hiddenartist.backend.global.redis.LockApplicationTimeClient;
import com.hiddenartist.backend.global.redis.RedisLockClient;
import com.hiddenartist.backend.global.redis.RefreshTokenClient;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@DataRedisTest
@Import({TestRedisConfig.class, LockApplicationTimeClient.class, RefreshTokenClient.class, RedisLockClient.class})
public abstract class AbstractRedisTest {

  @DynamicPropertySource
  static void configureProperty(DynamicPropertyRegistry registry) {
    TestContainerSetup.setDynamicProperties(registry);
  }

}