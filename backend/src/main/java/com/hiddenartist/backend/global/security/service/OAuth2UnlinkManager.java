package com.hiddenartist.backend.global.security.service;

import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import com.hiddenartist.backend.global.exception.type.SecurityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2UnlinkManager {

  private final KakaoOAuth2Unlink kakaoOAuth2Unlink;

  public boolean unlink(ProviderType providerType, String value) {
    if (Objects.equals(providerType, ProviderType.KAKAO)) {
      return kakaoOAuth2Unlink.unlink(value);
    } else {
      // OAuth2 Provider 확장 시 수정
      throw new SecurityException(ServiceErrorCode.PROVIDER_NOT_FOUND);
    }
  }

}