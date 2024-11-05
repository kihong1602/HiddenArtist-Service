package com.hiddenartist.backend.global.exception.response;

import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;

public class ServiceErrorDetail extends ErrorDetail {

  public ServiceErrorDetail(ServiceErrorCode errorCode) {
    super(errorCode);
  }

}