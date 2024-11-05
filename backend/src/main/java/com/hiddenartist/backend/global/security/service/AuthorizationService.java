package com.hiddenartist.backend.global.security.service;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.repository.AccountRepository;
import com.hiddenartist.backend.global.security.auth.OAuth2UserAttributes;
import com.hiddenartist.backend.global.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

  private final AccountRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public Account validateUser(OAuth2UserAttributes oAuth2UserAttributes) {
    return memberRepository.findByEmail(oAuth2UserAttributes.email()).orElseGet(() -> save(oAuth2UserAttributes));
  }

  private Account save(OAuth2UserAttributes oAuth2UserAttributes) {
    String encodedPassword = passwordEncoder.encodedOAuth2Password(
        oAuth2UserAttributes.providerType(), oAuth2UserAttributes.email());
    Account account = oAuth2UserAttributes.toAccount(encodedPassword);
    return memberRepository.save(account);
  }
}