package com.hiddenartist.backend.domain.account.persistence.type;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProviderTypeTest {

  @Test
  @DisplayName("ProviderType Find Method Test")
  void find() {
    String inputProviderTypeString = "kakao";
    ProviderType providerType = ProviderType.find(inputProviderTypeString);
    assertThat(providerType).isEqualTo(ProviderType.KAKAO);
  }

}