package com.hiddenartist.backend.global.security.handler;

import com.hiddenartist.backend.global.jwt.GenerateToken;
import com.hiddenartist.backend.global.jwt.TokenService;
import com.hiddenartist.backend.global.security.auth.PrincipalDetails;
import com.hiddenartist.backend.global.utils.CookieManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenService tokenService;

  @Value("${redirect-url.login-success}")
  private String REDIRECT_URL;

  /**
   * OAuth2 로그인 성공시 Authentication에 담긴 사용자 데이터를 기반으로 <br> AccessToken, RefreshToken 을 생성합니다. 두 데이터 모두 HttpOnly Cookie로
   * 저장합니다.
   */
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    GenerateToken tokens = generateToken(authentication);
    CookieManager.storeTokenInCookie(tokens, response);
    response.sendRedirect(REDIRECT_URL);
    clearSession(request);
  }

  private GenerateToken generateToken(Authentication authentication) {
    PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
    String email = user.getUsername();
    String authorities = user.getAuthorities()
                             .stream()
                             .map(GrantedAuthority::getAuthority)
                             .collect(Collectors.joining(","));
    return tokenService.createJWT(email, authorities);
  }

  private void clearSession(HttpServletRequest request) {
    SecurityContextHolder.clearContext();
    HttpSession session = request.getSession(false);
    if (Objects.nonNull(session)) {
      session.invalidate();
    }
  }

}