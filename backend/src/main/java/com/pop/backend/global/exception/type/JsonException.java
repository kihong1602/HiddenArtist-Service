package com.pop.backend.global.exception.type;

public class JsonException extends ServiceException {

  public JsonException(ServiceErrorCode serviceErrorCode) {
    super(serviceErrorCode);
  }

}