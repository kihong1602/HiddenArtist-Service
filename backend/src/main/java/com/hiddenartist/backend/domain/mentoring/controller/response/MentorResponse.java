package com.hiddenartist.backend.domain.mentoring.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.mentor.persistence.Mentor;

public record MentorResponse(
    String name,

    @JsonProperty("profile_image")
    String profileImage,

    String career,

    String organization
) {

  public static MentorResponse create(Mentor mentor) {
    Account account = mentor.getAccount();
    return new MentorResponse(
        account.getNickname(),
        account.getProfileImage(),
        mentor.getCareer().getDescription(),
        mentor.getOrganization()
    );
  }

}
