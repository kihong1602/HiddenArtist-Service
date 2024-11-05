package com.hiddenartist.backend.global.security.handler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@ExtendWith(MockitoExtension.class)
class AdminLoginSuccessHandlerTest {

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  Authentication authentication;

  @Mock
  HttpSession session;

  @Mock
  SecurityContext securityContext;

  @InjectMocks
  private AdminLoginSuccessHandler adminLoginSuccessHandler;

  @Test
  @DisplayName("인증 성공시 SecurityContext 및 세션 설정, 상태코드 200 반환")
  void loginSuccessHandlerTest() throws ServletException, IOException {
    //given
    SecurityContextHolder.setContext(securityContext);
    given(request.getSession()).willReturn(session);
    //when
    adminLoginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    //then
    verify(securityContext).setAuthentication(authentication);
    verify(session).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
    verify(response).setStatus(HttpServletResponse.SC_OK);
  }

}