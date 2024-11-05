package com.hiddenartist.backend.global.security.handler;

import com.hiddenartist.backend.global.exception.response.ErrorResponse;
import com.hiddenartist.backend.global.exception.response.ServiceErrorDetail;
import com.hiddenartist.backend.global.exception.response.ServiceErrorResponse;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final JsonUtils jsonUtils;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    ErrorResponse errorResponse = new ServiceErrorResponse(HttpStatus.UNAUTHORIZED,
        new ServiceErrorDetail(ServiceErrorCode.UNAUTHORIZED_ERROR));
    String body = jsonUtils.serializedObjectToJson(errorResponse);
    response.setStatus(errorResponse.getStatus().value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(body);
  }

}