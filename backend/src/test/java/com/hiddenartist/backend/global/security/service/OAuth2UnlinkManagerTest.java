package com.hiddenartist.backend.global.security.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import com.hiddenartist.backend.global.exception.type.SecurityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OAuth2UnlinkManagerTest {


  @Mock
  private KakaoOAuth2Unlink kakaoOAuth2Unlink;

  @InjectMocks
  private OAuth2UnlinkManager oAuth2UnlinkManager;

  @Test
  @DisplayName("올바른 ProviderType이 입력되면 Unlink 진행")
  void unlinkSuccessTest() {
    //given
    ProviderType providerType = ProviderType.KAKAO;
    String value = "test value";
    given(kakaoOAuth2Unlink.unlink(anyString())).willReturn(true);
    //when
    boolean result = oAuth2UnlinkManager.unlink(providerType, value);
    //then
    assertThat(result).isEqualTo(true);
  }

  @Test
  @DisplayName("올바르지 않은 ProviderType, 또는 미구현된 ProviderType이 입력되면 SecurityException 발생")
  void unlinkFailureTest() {
    //given
    ProviderType providerType = ProviderType.NAVER;
    String value = "test value";

    //when then
    assertThatThrownBy(() -> oAuth2UnlinkManager.unlink(providerType, value))
        .isInstanceOf(SecurityException.class)
        .hasMessage(ServiceErrorCode.PROVIDER_NOT_FOUND.getErrorMessage());
  }

}