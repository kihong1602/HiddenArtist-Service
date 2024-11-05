package com.hiddenartist.backend.domain.account.persistence.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ProviderType {
  LOCAL, KAKAO, GOOGLE, NAVER;

  private static final Map<String, ProviderType> CACHED_TYPES =
      Collections.unmodifiableMap(
          Arrays.stream(values()).collect(Collectors.toMap(ProviderType::name, Function.identity()))
      );

  public static ProviderType find(String providerType) {
    return Optional.ofNullable(CACHED_TYPES.get(providerType.toUpperCase())).orElse(LOCAL);
  }

}