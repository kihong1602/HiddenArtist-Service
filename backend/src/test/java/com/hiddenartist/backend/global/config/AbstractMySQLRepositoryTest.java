package com.hiddenartist.backend.global.config;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public abstract class AbstractMySQLRepositoryTest {

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    TestContainerSetup.setDynamicProperties(registry);
  }

}
