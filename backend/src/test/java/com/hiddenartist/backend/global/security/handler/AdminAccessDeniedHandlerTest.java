package com.hiddenartist.backend.global.security.handler;

import static org.mockito.BDDMockito.verify;

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
import org.springframework.security.access.AccessDeniedException;

@ExtendWith(MockitoExtension.class)
class AdminAccessDeniedHandlerTest {

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  AccessDeniedException accessDeniedException;

  @InjectMocks
  private AdminAccessDeniedHandler adminAccessDeniedHandler;

  @Test
  @DisplayName("ADMIN API에 대해 접근권한 부족 시 지정된 URL로 리디렉션")
  void accessDeniedHandlerTest() throws IOException, ServletException {
    //given
    String expectedRedirectUrl = "http://localhost:8080/admin/signin";
    //when
    adminAccessDeniedHandler.handle(request, response, accessDeniedException);
    //then
    verify(response).sendRedirect(expectedRedirectUrl);
  }
}