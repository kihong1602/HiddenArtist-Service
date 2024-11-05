package com.hiddenartist.backend.domain.account.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.account.persistence.Account;

public record AccountGetSimpleResponse(
    String nickname,

    @JsonProperty("profile_image")
    String profileImage
) {

  public static AccountGetSimpleResponse of(Account account) {
    return new AccountGetSimpleResponse(account.getNickname(), account.getProfileImage());
  }

}