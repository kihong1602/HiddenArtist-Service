package com.hiddenartist.backend.global.security.auth;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import java.io.Serializable;

public record SecurityUserInfo(
    String email,
    String nickname,
    String password,
    ProviderType providerType
) implements Serializable {

  public static SecurityUserInfo convert(Account account) {
    return new SecurityUserInfo(account.getEmail(), account.getNickname(), account.getPassword(),
        account.getProviderType());
  }

}