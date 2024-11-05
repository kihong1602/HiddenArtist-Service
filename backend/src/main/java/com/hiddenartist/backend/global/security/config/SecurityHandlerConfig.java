package com.hiddenartist.backend.global.security.config;

import com.hiddenartist.backend.global.security.handler.AdminAccessDeniedHandler;
import com.hiddenartist.backend.global.security.handler.AdminAuthenticationEntryPoint;
import com.hiddenartist.backend.global.security.handler.ApiAccessDeniedHandler;
import com.hiddenartist.backend.global.security.handler.ApiAuthenticationEntryPoint;
import com.hiddenartist.backend.global.security.handler.OAuth2LoginSuccessHandler;
import com.hiddenartist.backend.global.security.handler.OAuth2LogoutHandler;
import com.hiddenartist.backend.global.security.handler.OAuth2LogoutSuccessHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@RequiredArgsConstructor
public class SecurityHandlerConfig {

  private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

  private final OAuth2LogoutHandler oAuth2LogoutHandler;

  private final OAuth2LogoutSuccessHandler oAuth2LogoutSuccessHandler;

  private final AdminAccessDeniedHandler adminAccessDeniedHandler;

  private final AdminAuthenticationEntryPoint adminAuthenticationEntryPoint;

  private final ApiAccessDeniedHandler apiAccessDeniedHandler;

  private final ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;

}