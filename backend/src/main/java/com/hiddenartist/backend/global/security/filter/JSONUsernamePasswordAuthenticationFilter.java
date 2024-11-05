package com.hiddenartist.backend.global.security.filter;

import com.hiddenartist.backend.global.exception.type.SecurityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.security.handler.AdminLoginFailureHandler;
import com.hiddenartist.backend.global.security.handler.AdminLoginSuccessHandler;
import com.hiddenartist.backend.global.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JSONUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final String LOGIN_URL = "/admin/accounts/signin";
  private static final AntPathRequestMatcher LOGIN_REQUEST_MATCHER =
      new AntPathRequestMatcher(LOGIN_URL, HttpMethod.POST.name());
  private final JsonUtils jsonUtils;

  protected JSONUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
      AdminLoginSuccessHandler loginSuccessHandler, AdminLoginFailureHandler failureHandler, JsonUtils jsonUtils) {
    super(LOGIN_REQUEST_MATCHER, authenticationManager);
    setAuthenticationSuccessHandler(loginSuccessHandler);
    setAuthenticationFailureHandler(failureHandler);
    this.jsonUtils = jsonUtils;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    String contentType = request.getContentType();
    validateContentType(contentType);

    LoginData loginData = jsonUtils.deserializedJsonToObject(request.getInputStream(), LoginData.class);
    Authentication authRequest = validateBodyData(loginData);

    return super.getAuthenticationManager().authenticate(authRequest);
  }

  private void validateContentType(final String REQUEST_CONTENT_TYPE) {
    if (Objects.isNull(REQUEST_CONTENT_TYPE) || !REQUEST_CONTENT_TYPE.equals(MediaType.APPLICATION_JSON_VALUE)) {
      throw new SecurityException(ServiceErrorCode.INVALID_CONTENT_TYPE);
    }
  }

  private Authentication validateBodyData(LoginData loginData) {
    if (StringUtils.hasText(loginData.email) || StringUtils.hasText(loginData.password())) {
      return new UsernamePasswordAuthenticationToken(loginData.email, loginData.password);
    }
    throw new SecurityException(ServiceErrorCode.BODY_DATA_NOT_FOUND);
  }

  private record LoginData(
      String email,
      String password
  ) {

  }

}