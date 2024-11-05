package com.hiddenartist.backend.global.exception.type;

public class MentoringException extends ServiceException {

  public MentoringException(ServiceErrorCode serviceErrorCode) {
    super(serviceErrorCode);
  }

}