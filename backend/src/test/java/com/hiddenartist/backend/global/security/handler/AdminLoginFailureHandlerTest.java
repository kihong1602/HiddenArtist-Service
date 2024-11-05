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
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class AdminLoginFailureHandlerTest {

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  AuthenticationException authenticationException;

  @InjectMocks
  private AdminLoginFailureHandler adminLoginFailureHandler;

  @Test
  @DisplayName("ADMIN 로그인 실패 시 에러 응답을 발생시킨다.")
  void adminLoginFailureTest() throws ServletException, IOException {
    //given
    int status = HttpStatus.BAD_REQUEST.value();
    String contentType = MediaType.APPLICATION_JSON_VALUE;
    String characterEncoding = StandardCharsets.UTF_8.name();
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    given(response.getWriter()).willReturn(printWriter);
    given(authenticationException.getMessage()).willReturn("로그인 에러");

    //when
    adminLoginFailureHandler.onAuthenticationFailure(request, response, authenticationException);

    //then
    verify(response).setStatus(status);
    verify(response).setContentType(contentType);
    verify(response).setCharacterEncoding(characterEncoding);
    assertThat(stringWriter.toString()).isEqualTo(authenticationException.getMessage());
  }
}