package com.hiddenartist.backend.global.security.config;

import com.hiddenartist.backend.global.jwt.TokenService;
import com.hiddenartist.backend.global.security.filter.JSONUsernamePasswordAuthenticationFilter;
import com.hiddenartist.backend.global.security.filter.JWTAuthenticationFilter;
import com.hiddenartist.backend.global.security.filter.SecurityExceptionHandleFilter;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SecurityFilterConfig {

  private final JWTAuthenticationFilter jwtAuthenticationFilter;
  private final SecurityExceptionHandleFilter exceptionHandleFilter;
  private final JSONUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

  public SecurityFilterConfig(TokenService tokenService,
      SecurityExceptionHandleFilter exceptionHandleFilter,
      JSONUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter) {
    this.jwtAuthenticationFilter = new JWTAuthenticationFilter(tokenService);
    this.exceptionHandleFilter = exceptionHandleFilter;
    this.usernamePasswordAuthenticationFilter = usernamePasswordAuthenticationFilter;
  }

}