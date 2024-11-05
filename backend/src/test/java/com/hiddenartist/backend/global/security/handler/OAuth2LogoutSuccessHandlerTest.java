package com.hiddenartist.backend.global.security.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class OAuth2LogoutSuccessHandlerTest {

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  Authentication authentication;

  @InjectMocks
  private OAuth2LogoutSuccessHandler oAuth2LogoutSuccessHandler;

  @BeforeEach
  void init() {
    ReflectionTestUtils.setField(oAuth2LogoutSuccessHandler, "REDIRECT_URL", "http://test.com/");
  }

  @Test
  @DisplayName("로그아웃 성공시 인덱스 페이지로 Redirect")
  void oAuth2LogoutSuccessHandler() throws ServletException, IOException {
    //given
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    given(response.getWriter()).willReturn(printWriter);
    //when
    oAuth2LogoutSuccessHandler.onLogoutSuccess(request, response, authentication);
    //then
    verify(response).sendRedirect("http://test.com/");
    assertThat(stringWriter.toString()).isEqualTo("로그아웃이 완료되었습니다. 홈페이지로 이동합니다.");
  }
}