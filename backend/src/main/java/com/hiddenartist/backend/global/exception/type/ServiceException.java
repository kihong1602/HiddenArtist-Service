package com.hiddenartist.backend.global.exception.type;

import com.hiddenartist.backend.global.exception.response.ServiceErrorDetail;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ServiceException extends RuntimeException {

  private final HttpStatus status;
  private final ServiceErrorDetail errorDetail;

  protected ServiceException(ServiceErrorCode serviceErrorCode) {
    super(serviceErrorCode.getErrorMessage());
    this.status = serviceErrorCode.getStatus();
    this.errorDetail = new ServiceErrorDetail(serviceErrorCode);
  }

}