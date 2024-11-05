package com.hiddenartist.backend.global.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookieNames {

  ACCESS_TOKEN("AccessToken"),
  REFRESH_TOKEN("RefreshToken");

  private final String name;

}