package com.hiddenartist.backend.global.exception.type;

public class RedisLockException extends ServiceException {

  public RedisLockException(ServiceErrorCode serviceErrorCode) {
    super(serviceErrorCode);
  }

}