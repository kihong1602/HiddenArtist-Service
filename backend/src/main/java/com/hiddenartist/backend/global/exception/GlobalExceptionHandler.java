package com.hiddenartist.backend.global.exception;

import com.hiddenartist.backend.global.exception.response.ErrorResponse;
import com.hiddenartist.backend.global.exception.response.ServiceErrorResponse;
import com.hiddenartist.backend.global.exception.type.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {ServiceException.class})
  public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
    log.error("{}: {} \n {}", ex.getClass().getSimpleName(), ex.getErrorDetail().getMessage(), ex.getStackTrace()[0]);
    ErrorResponse errorResponse = new ServiceErrorResponse(ex);
    return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
  }

}