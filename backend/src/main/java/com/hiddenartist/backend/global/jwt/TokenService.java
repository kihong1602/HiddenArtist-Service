package com.hiddenartist.backend.global.jwt;

import com.hiddenartist.backend.global.exception.type.SecurityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.type.JWTValidationResult;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final JWTProvider jwtProvider;
  private final RefreshTokenService refreshTokenService;

  public GenerateToken createJWT(String email, String authorities) {
    GenerateToken generateToken = jwtProvider.generateToken(email, authorities);
    refreshTokenService.save(generateToken.refreshToken());
    return generateToken;
  }

  public GenerateToken refreshJWT(String token) {
    String email = jwtProvider.getEmail(token);
    RefreshToken refreshToken = refreshTokenService.findRefreshTokenBy(email);
    if (!token.equals(refreshToken.getRefreshToken())) {
      throw new SecurityException(ServiceErrorCode.INVALID_REFRESH_TOKEN);
    }
    String authorities = jwtProvider.getAuthorities(token);
    return createJWT(email, authorities);
  }

  public JWTValidationResult validateToken(String token) {
    if (Objects.isNull(token)) {
      throw new SecurityException(ServiceErrorCode.TOKEN_NOT_FOUND);
    }
    return jwtProvider.verifyToken(token);
  }

  public void saveAuthentication(String token) {
    String email = jwtProvider.getEmail(token);
    String role = jwtProvider.getAuthorities(token);
    List<SimpleGrantedAuthority> authorities = Arrays.stream(role.split(",")).map(SimpleGrantedAuthority::new).toList();
    Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  public void removeRefreshToken(String refreshToken) {
    String email = jwtProvider.getEmail(refreshToken);
    refreshTokenService.removeRefreshToken(email);
  }

}