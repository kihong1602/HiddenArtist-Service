package com.hiddenartist.backend.domain.mentoring.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringApplicationStatus;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public abstract class MentoringApplicationResponse {

  private String token;

  @JsonProperty("application_time")
  private LocalDateTime applicationTime;

  private String status;

  protected MentoringApplicationResponse(String token, LocalDateTime applicationTime, MentoringApplicationStatus status) {
    this.token = EntityToken.MENTORING_APPLICATION.extractToken(token);
    this.applicationTime = applicationTime;
    this.status = status.name();
  }

}