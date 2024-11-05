package com.hiddenartist.backend.global.config;

import com.hiddenartist.backend.domain.search.persistence.repository.SearchRepository;
import com.hiddenartist.backend.domain.search.persistence.repository.SearchRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;

@TestConfiguration
@EnableJpaAuditing
public class TestConfig {

  @Autowired
  JdbcTemplate jdbcTemplate;
  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  @Bean
  public TestDataInitializer testDataInitializer() {
    return new TestDataInitializer(jdbcTemplate);
  }

  @Bean
  public SearchRepository searchRepository() {
    return new SearchRepositoryImpl(jpaQueryFactory());
  }

}