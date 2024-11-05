package com.hiddenartist.backend.global.security.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.repository.AccountRepository;
import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import com.hiddenartist.backend.domain.account.persistence.type.Role;
import com.hiddenartist.backend.global.exception.type.EntityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.security.auth.PrincipalDetails;
import com.hiddenartist.backend.global.security.auth.SecurityUserInfo;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private CustomUserDetailsService userDetailsService;

  @Test
  @DisplayName("계정이 존재하면 PrincipalDetails 반환")
  void loadUserByUsernameSuccessTest() {
    //given
    final String testUsername = "test@test.com";
    final Account account = Account.builder()
                                   .email(testUsername)
                                   .password("test_password")
                                   .nickname("test admin")
                                   .role(Role.ADMIN)
                                   .providerType(ProviderType.LOCAL)
                                   .build();
    final SecurityUserInfo userInfo = SecurityUserInfo.convert(account);
    given(accountRepository.findByEmail(any(String.class))).willReturn(Optional.of(account));
    //when
    UserDetails userDetails = userDetailsService.loadUserByUsername(testUsername);
    //then
    assertThat(userDetails).isNotNull().isInstanceOf(PrincipalDetails.class);
    assertThat(((PrincipalDetails) userDetails).getUserInfo()).isEqualTo(userInfo);
  }

  @Test
  @DisplayName("계정이 존재하지 않으면 EntityException 발생")
  void loadUserByUsernameFailureTest() {
    //given
    final String testUsername = "test@test.com";
    given(accountRepository.findByEmail(any(String.class))).willReturn(Optional.empty());
    //when then
    assertThatThrownBy(() -> userDetailsService.loadUserByUsername(testUsername))
        .isInstanceOf(EntityException.class)
        .hasMessage(ServiceErrorCode.USER_NOT_FOUND.getErrorMessage());
  }

}