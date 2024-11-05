package com.hiddenartist.backend.global.security.handler;

import static org.mockito.Mockito.verify;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class AdminAuthenticationEntryPointTest {


  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  AuthenticationException authenticationException;

  @InjectMocks
  private AdminAuthenticationEntryPoint adminAuthenticationEntryPoint;

  @Test
  @DisplayName("ADMIN API에 대해 인증되지 않았다면 지정된 URL로 리디렉션")
  void authenticationEntryPointTest() throws ServletException, IOException {
    //given
    String expectedRedirectUrl = "http://localhost:8080/admin/signin";
    //when
    adminAuthenticationEntryPoint.commence(request, response, authenticationException);
    //then
    verify(response).sendRedirect(expectedRedirectUrl);
  }
}