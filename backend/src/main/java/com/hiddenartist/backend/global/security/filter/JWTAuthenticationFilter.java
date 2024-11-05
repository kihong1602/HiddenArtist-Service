package com.hiddenartist.backend.global.security.filter;

import com.hiddenartist.backend.global.exception.type.SecurityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.jwt.GenerateToken;
import com.hiddenartist.backend.global.jwt.TokenService;
import com.hiddenartist.backend.global.type.CookieNames;
import com.hiddenartist.backend.global.type.EndPoint;
import com.hiddenartist.backend.global.type.JWTValidationResult;
import com.hiddenartist.backend.global.utils.CookieManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final PathMatcher pathMatcher = new AntPathMatcher();
  private final List<EndPoint> tokenFreeEndPoints = List.of(
      EndPoint.create("/api/accounts/signin/**", HttpMethod.GET),
      EndPoint.create("/api/artists", HttpMethod.GET),
      EndPoint.create("/api/artists/**", HttpMethod.GET),
      EndPoint.create("/api/artists/popular", HttpMethod.GET),
      EndPoint.create("/api/artists/{token}/signature-artworks", HttpMethod.GET),
      EndPoint.create("/api/artworks/**", HttpMethod.GET),
      EndPoint.create("/api/artworks/recommend", HttpMethod.GET),
      EndPoint.create("/api/search/**", HttpMethod.GET)
  );

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (isTokenFreeEndPoint(request)) {
      doFilter(request, response, filterChain);
      return;
    }

    String accessToken = CookieManager.getCookie(CookieNames.ACCESS_TOKEN.getName(), request);

    JWTValidationResult validationResult = tokenService.validateToken(accessToken);
    switch (validationResult) {
      case VALID -> {
        tokenService.saveAuthentication(accessToken);
        filterChain.doFilter(request, response);
      }
      case EXPIRED -> {
        String refreshToken = CookieManager.getCookie(CookieNames.REFRESH_TOKEN.getName(), request);
        refreshToken(refreshToken, response);
        filterChain.doFilter(request, response);
      }
      case INVALID -> throw new SecurityException(ServiceErrorCode.INVALID_ACCESS_TOKEN);
    }
  }

  private boolean isTokenFreeEndPoint(HttpServletRequest request) {
    String requestUri = request.getRequestURI();
    String requestMethod = request.getMethod().toUpperCase();
    return tokenFreeEndPoints.stream().anyMatch(endPoint -> isMatches(endPoint, requestUri, requestMethod));
  }

  private boolean isMatches(EndPoint endPoint, String requestUri, String httpMethod) {
    return pathMatcher.match(endPoint.pattern(), requestUri) && endPoint.method().matches(httpMethod);
  }

  private void refreshToken(String token, HttpServletResponse response) throws SecurityException {
    JWTValidationResult validationResult = tokenService.validateToken(token);
    if (validationResult.isNotValid()) {
      throw new SecurityException(ServiceErrorCode.INVALID_REFRESH_TOKEN);
    }
    GenerateToken generateToken = tokenService.refreshJWT(token);
    CookieManager.storeTokenInCookie(generateToken, response);
  }

}