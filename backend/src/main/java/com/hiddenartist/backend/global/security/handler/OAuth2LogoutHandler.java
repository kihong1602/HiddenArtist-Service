package com.hiddenartist.backend.global.security.handler;

import com.hiddenartist.backend.global.jwt.TokenService;
import com.hiddenartist.backend.global.type.CookieNames;
import com.hiddenartist.backend.global.utils.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LogoutHandler implements LogoutHandler {

  private final TokenService tokenService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    String refreshToken = CookieManager.getCookie(CookieNames.REFRESH_TOKEN.getName(), request);
    tokenService.removeRefreshToken(refreshToken);

    CookieManager.removeCookie(CookieNames.ACCESS_TOKEN.getName(), response);
    CookieManager.removeCookie(CookieNames.REFRESH_TOKEN.getName(), response);
  }

}