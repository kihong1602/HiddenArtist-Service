package com.hiddenartist.backend.global.security.handler;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.type.Role;
import com.hiddenartist.backend.global.jwt.GenerateToken;
import com.hiddenartist.backend.global.jwt.RefreshToken;
import com.hiddenartist.backend.global.jwt.TokenService;
import com.hiddenartist.backend.global.security.auth.PrincipalDetails;
import com.hiddenartist.backend.global.type.CookieNames;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class OAuth2LoginSuccessHandlerTest {

  private static final String REDIRECT_URL = "http://test.com/login-success";
  private static final String email = "test@test.com";

  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private HttpSession session;
  @Mock
  private Authentication authentication;
  @Mock
  private TokenService tokenService;
  @InjectMocks
  private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

  @BeforeEach
  void init() {
    ReflectionTestUtils.setField(oAuth2LoginSuccessHandler, "REDIRECT_URL", REDIRECT_URL);
  }

  @Test
  @DisplayName("OAuth2 인증이 완료되면 JWT 저장, 인증에 사용한 Session 삭제")
  void oAuth2LoginSuccessTest() throws ServletException, IOException {
    //given
    Account account = Account.builder().email(email).role(Role.USER).build();
    Object principalDetails = PrincipalDetails.create(account);
    String testAccessTokenValue = "TestAccessToken";
    String testRefreshTokenValue = "TestRefreshToken";
    GenerateToken generateToken = new GenerateToken(testAccessTokenValue,
        new RefreshToken(email, testRefreshTokenValue));
    String testAccessToken = getAccessToken(CookieNames.ACCESS_TOKEN.getName(), testAccessTokenValue);
    String testRefreshToken = getRefreshToken(CookieNames.REFRESH_TOKEN.getName(), testRefreshTokenValue);

    given(authentication.getPrincipal()).willReturn(principalDetails);
    given(tokenService.createJWT(anyString(), anyString())).willReturn(generateToken);
    given(request.getSession(false)).willReturn(session);

    //when
    oAuth2LoginSuccessHandler.onAuthenticationSuccess(request, response, authentication);

    //then
    verify(response).addHeader(HttpHeaders.SET_COOKIE, testAccessToken);
    verify(response).addHeader(HttpHeaders.SET_COOKIE, testRefreshToken);
    verify(response).sendRedirect(REDIRECT_URL);
    verify(session).invalidate();
  }

  private String getAccessToken(String key, String value) {
    return ResponseCookie.from(key, value)
                         .maxAge(Duration.ofMinutes(30))
                         .httpOnly(true)
                         .sameSite("Strict")
                         .path("/")
                         .build()
                         .toString();
  }

  private String getRefreshToken(String key, String value) {
    return ResponseCookie.from(key, value)
                         .maxAge(Duration.ofDays(7))
                         .httpOnly(true)
                         .sameSite("Strict")
                         .path("/")
                         .build()
                         .toString();
  }

}