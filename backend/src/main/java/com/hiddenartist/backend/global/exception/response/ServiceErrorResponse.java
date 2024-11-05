package com.hiddenartist.backend.global.exception.response;

import com.hiddenartist.backend.global.exception.type.ServiceException;
import org.springframework.http.HttpStatus;

public class ServiceErrorResponse extends ErrorResponse {

  public ServiceErrorResponse(ServiceException ex) {
    super(ex.getStatus(), ex.getErrorDetail());
  }

  public ServiceErrorResponse(HttpStatus httpStatus, ErrorDetail errorDetail) {
    super(httpStatus, errorDetail);
  }

}