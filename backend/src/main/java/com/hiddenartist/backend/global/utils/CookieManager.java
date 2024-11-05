package com.hiddenartist.backend.global.utils;

import com.hiddenartist.backend.global.jwt.GenerateToken;
import com.hiddenartist.backend.global.type.CookieNames;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieManager {

  private static final boolean HTTP_ONLY = true;
  private static final String SAME_SITE = "Strict";
  private static final String COOKIE_PATH = "/";
  private static final String EMPTY = "";
  private static final Duration THIRTY_MINUTES = Duration.ofMinutes(30L);
  private static final Duration ONE_WEEKS = Duration.ofDays(7L);

  public static void addCookie(String key, String value, Duration maxAge, HttpServletResponse response) {
    ResponseCookie cookie = ResponseCookie.from(key, value)
                                          .maxAge(maxAge)
                                          .httpOnly(HTTP_ONLY)
                                          .sameSite(SAME_SITE)
                                          .path(COOKIE_PATH)
                                          .build();
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  public static void removeCookie(String key, HttpServletResponse response) {

    ResponseCookie cookie = ResponseCookie.from(key, EMPTY)
                                          .maxAge(0L)
                                          .httpOnly(HTTP_ONLY)
                                          .sameSite(SAME_SITE)
                                          .path(COOKIE_PATH)
                                          .build();
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  public static String getCookie(String key, HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (Objects.isNull(cookies)) {
      return null;
    }
    return Arrays.stream(cookies)
                 .filter(cookie -> cookie.getName().equals(key))
                 .map(Cookie::getValue)
                 .findFirst()
                 .orElse(null);
  }

  public static void storeTokenInCookie(GenerateToken generateToken, HttpServletResponse response) {
    String accessToken = generateToken.accessToken();
    String refreshToken = generateToken.refreshToken().getRefreshToken();
    addCookie(CookieNames.ACCESS_TOKEN.getName(), accessToken, THIRTY_MINUTES, response);
    addCookie(CookieNames.REFRESH_TOKEN.getName(), refreshToken, ONE_WEEKS, response);
  }

}