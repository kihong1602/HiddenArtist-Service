package com.hiddenartist.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  // 추후 timeout, ThreadPool 설정 예정
  @Bean
  public RestClient restClient() {
    return RestClient.create();
  }

}