package com.hiddenartist.backend.domain.account.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.account.persistence.Account;

public record AccountGetDetailResponse(
    String nickname,

    @JsonProperty("profile_image")
    String profileImage,

    String email
) {

  public static AccountGetDetailResponse of(Account account) {
    return new AccountGetDetailResponse(account.getNickname(), account.getProfileImage(), account.getEmail());
  }
}
