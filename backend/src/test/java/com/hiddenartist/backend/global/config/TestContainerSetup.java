package com.hiddenartist.backend.global.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;

public class TestContainerSetup {

  private static final String MYSQL_IMAGE = "mysql:8.0.34";

  private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(MYSQL_IMAGE)
      .withDatabaseName("pop")
      .withUsername("test")
      .withPassword("test")
      .withCommand("--ft_min_word_len=2", "--innodb_ft_min_token_size=2")
      .withInitScript("schema.sql");
  private static final GenericContainer<?> REDIS_CONTAINER = new GenericContainer<>("redis:latest")
      .withExposedPorts(6379)
      .withEnv("REDIS_PASSWORD", "1234")
      .withCommand("redis-server", "--requirepass", "1234");


  static {
    MY_SQL_CONTAINER.start();
    REDIS_CONTAINER.start();
  }

  public static void setDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);

    registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
    registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379));
    registry.add("spring.data.redis.password", () -> "1234");
  }

}
