package com.hiddenartist.backend.global.security.handler;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.hiddenartist.backend.global.jwt.TokenService;
import com.hiddenartist.backend.global.type.CookieNames;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class OAuth2LogoutHandlerTest {

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  Authentication authentication;

  @Mock
  TokenService tokenService;

  @InjectMocks
  private OAuth2LogoutHandler oAuth2LogoutHandler;

  @Test
  @DisplayName("로그아웃 시 Cookie에서 JWT삭제, Redis에서 RefreshToken 삭제")
  void oAuth2LogoutTest() {
    //given
    Cookie[] cookies = createCookies();
    given(request.getCookies()).willReturn(cookies);
    doNothing().when(tokenService).removeRefreshToken(anyString());

    //when
    oAuth2LogoutHandler.logout(request, response, authentication);
    //then
    verify(tokenService).removeRefreshToken("TestRefreshToken");
    verify(response).addHeader(HttpHeaders.SET_COOKIE, getCookieValue(CookieNames.ACCESS_TOKEN.getName()));
    verify(response).addHeader(HttpHeaders.SET_COOKIE, getCookieValue(CookieNames.REFRESH_TOKEN.getName()));
  }

  private Cookie[] createCookies() {
    Cookie accessTokenCookie = new Cookie(CookieNames.ACCESS_TOKEN.getName(), "TestAccessToken");
    Cookie refreshTokenCookie = new Cookie(CookieNames.REFRESH_TOKEN.getName(), "TestRefreshToken");
    return new Cookie[]{accessTokenCookie, refreshTokenCookie};
  }

  private String getCookieValue(String key) {
    return ResponseCookie.from(key, "")
                         .maxAge(0L)
                         .httpOnly(true)
                         .sameSite("Strict")
                         .path("/")
                         .build().toString();
  }

}