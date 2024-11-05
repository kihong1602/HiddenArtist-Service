package com.hiddenartist.backend.domain.account.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringApplicationResponse;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringApplicationStatus;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AccountGetMentoringApplicationResponse extends MentoringApplicationResponse {

  @JsonProperty("mentoring_info")
  private MentoringInfo mentoringInfo;

  public AccountGetMentoringApplicationResponse(String mentoringApplicationToken, LocalDateTime applicationTime,
      MentoringApplicationStatus status, String name, Integer amount, String mentoringToken, String mentorName) {
    super(mentoringApplicationToken, applicationTime, status);
    this.mentoringInfo = new MentoringInfo(
        name,
        amount,
        EntityToken.MENTORING.extractToken(mentoringToken),
        mentorName
    );
  }

  public record MentoringInfo(
      String name,

      Integer amount,

      String token,

      @JsonProperty("mentor_name")
      String mentorName
  ) {

  }

}