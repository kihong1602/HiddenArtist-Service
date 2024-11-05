package com.hiddenartist.backend.global.type;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@RequiredArgsConstructor
public enum EntityToken {

  ARTIST("Artist_"),
  ARTWORK("Artwork_"),
  EXHIBITION("Exhibition_"),
  MENTORING("Mentoring_"),
  MENTORING_APPLICATION("MentoringApplication_");

  private static final int TOKEN_LENGTH = 30;
  private final String prefix;

  public String createEntityToken() {
    return this.prefix + randomCharacter(TOKEN_LENGTH - this.prefix.length());
  }

  public String extractToken(String token) {
    return token.substring(this.prefix.length());
  }

  public String identifyToken(String tokenValue) {
    return this.prefix + tokenValue;
  }

  private String randomCharacter(int length) {
    return RandomStringUtils.randomAlphanumeric(length);
  }

}