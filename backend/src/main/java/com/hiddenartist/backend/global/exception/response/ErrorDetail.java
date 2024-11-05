package com.hiddenartist.backend.global.exception.response;

import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import lombok.Getter;

@Getter
public abstract class ErrorDetail {

  private final String code;
  private final String message;

  public ErrorDetail(ServiceErrorCode errorCode) {
    this.code = errorCode.name();
    this.message = errorCode.getErrorMessage();
  }

}