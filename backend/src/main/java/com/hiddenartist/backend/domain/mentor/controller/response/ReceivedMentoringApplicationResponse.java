package com.hiddenartist.backend.domain.mentor.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringApplicationResponse;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringApplicationStatus;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReceivedMentoringApplicationResponse extends MentoringApplicationResponse {

  @JsonProperty("mentoring_name")
  private String mentoringName;

  @JsonProperty("account_nickname")
  private String accountNickname;

  public ReceivedMentoringApplicationResponse(String token, LocalDateTime applicationTime,
      MentoringApplicationStatus status, String mentoringName, String accountNickname) {
    super(token, applicationTime, status);
    this.mentoringName = mentoringName;
    this.accountNickname = accountNickname;
  }

}