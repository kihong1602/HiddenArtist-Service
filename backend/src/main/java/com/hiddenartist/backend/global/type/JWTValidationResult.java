package com.hiddenartist.backend.global.type;

public enum JWTValidationResult {

  VALID, INVALID, EXPIRED;

  public boolean isNotValid() {
    return !this.equals(VALID);
  }

}