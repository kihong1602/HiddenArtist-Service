package com.hiddenartist.backend.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiddenartist.backend.global.exception.response.ErrorResponse;
import com.hiddenartist.backend.global.exception.response.ServiceErrorResponse;
import com.hiddenartist.backend.global.exception.type.ServiceException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityExceptionHandleFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (ServiceException e) {
      log.error("{}: {} \n {}", e.getClass().getSimpleName(), e.getErrorDetail().getMessage(), e.getStackTrace()[0]);
      HttpStatus status = e.getStatus();
      ErrorResponse errorResponse = new ServiceErrorResponse(e);
      response.setStatus(status.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      objectMapper.writeValue(response.getWriter(), errorResponse);
    }
  }
}
