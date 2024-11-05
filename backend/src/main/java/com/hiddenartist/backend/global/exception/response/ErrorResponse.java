package com.hiddenartist.backend.global.exception.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ErrorResponse {

  @JsonIgnore
  private final HttpStatus status;

  private final ErrorDetail details;

  protected ErrorResponse(HttpStatus status, ErrorDetail errorDetail) {
    this.status = status;
    this.details = errorDetail;
  }

}