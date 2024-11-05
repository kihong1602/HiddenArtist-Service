package com.hiddenartist.backend.global.security.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import com.hiddenartist.backend.global.security.auth.OAuth2UserAttributes;
import com.hiddenartist.backend.global.security.auth.PrincipalDetails;
import com.hiddenartist.backend.global.security.auth.SecurityUserInfo;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserServiceTest {

  @Mock
  private AuthorizationService authorizationService;

  @Mock
  private DefaultOAuth2UserService defaultOAuth2UserService;

  @InjectMocks
  private CustomOAuth2UserService oAuth2UserService;

  @Test
  @DisplayName("OAuth2 인증이 성공하면 PrincipalDetails 반환")
  void loadUserTest() {
    //given
    OAuth2UserRequest userRequest = createMockOAuth2UserRequest();
    OAuth2User oAuth2User = createMockOAUth2User();
    Account account = Account.builder().email("test@test.com").providerType(ProviderType.KAKAO).providerId(123456L)
                             .nickname("test nickname").profileImage("test_image_url").build();
    SecurityUserInfo userInfo = SecurityUserInfo.convert(account);
    //when
    given(defaultOAuth2UserService.loadUser(any(OAuth2UserRequest.class))).willReturn(oAuth2User);
    given(authorizationService.validateUser(any(OAuth2UserAttributes.class))).willReturn(account);
    //then
    OAuth2User result = oAuth2UserService.loadUser(userRequest);
    assertThat(result).isNotNull().isInstanceOf(PrincipalDetails.class);
    assertThat(((PrincipalDetails) result).getUserInfo()).isEqualTo(userInfo);
  }

  private OAuth2UserRequest createMockOAuth2UserRequest() {
    ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("kakao")
                                                              .clientId("test-client-id")
                                                              .clientSecret("test-client-secret")
                                                              .redirectUri("test.com/login/oauth2/code/kakao")
                                                              .authorizationUri("http://test-server/authorization")
                                                              .tokenUri("http://test-server/token")
                                                              .userInfoUri("http://test-server/userinfo")
                                                              .userNameAttributeName("id")
                                                              .authorizationGrantType(
                                                                  AuthorizationGrantType.AUTHORIZATION_CODE)
                                                              .scope("email", "profile")
                                                              .build();

    OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(
        TokenType.BEARER,
        "test-access-token",
        Instant.now(),
        Instant.now().plusSeconds(3600)
    );
    return new OAuth2UserRequest(clientRegistration, oAuth2AccessToken);
  }

  private OAuth2User createMockOAUth2User() {
    Map<String, Object> attributes = Map.of(
        "id", 123456L,
        "kakao_account", Map.of(
            "email", "test@test.com",
            "profile", Map.of(
                "nickname", "test nickname",
                "profile_image_url", "test_image_url"
            )
        )
    );
    return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "id");
  }

}