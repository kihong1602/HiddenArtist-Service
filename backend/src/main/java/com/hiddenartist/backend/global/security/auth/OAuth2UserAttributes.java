package com.hiddenartist.backend.global.security.auth;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import com.hiddenartist.backend.global.exception.type.SecurityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.security.auth.OAuth2ProviderRegistry.OAuth2ProviderType;
import java.util.Map;

public record OAuth2UserAttributes(
    Map<String, Object> attributes,
    ProviderType providerType,
    String email,
    String nickname,
    String image,
    Long providerId
) {

  public static OAuth2UserAttributes of(OAuth2ProviderType providerType, Map<String, Object> attributes) {
    switch (providerType) {
      case KAKAO -> {
        return fromKakao(providerType, attributes);
      }
      case NAVER -> {
        return fromNaver(providerType, attributes);
      }
      case GOOGLE -> {
        return fromGoogle(providerType, attributes);
      }
      default -> throw new SecurityException(ServiceErrorCode.PROVIDER_NOT_FOUND);
    }
  }

  @SuppressWarnings("unchecked")
  private static OAuth2UserAttributes fromKakao
      (OAuth2ProviderType providerType, Map<String, Object> attributes) {
    Long providerId = (Long) attributes.get("id");
    Map<String, Object> kakaoAttributes = extractAttributes(providerType.ATTRIBUTES_FIELD, attributes);
    String email = (String) kakaoAttributes.get(providerType.EMAIL_FIELD);

    kakaoAttributes = (Map<String, Object>) kakaoAttributes.get("profile");
    return new OAuth2UserAttributes(
        attributes,
        ProviderType.find(providerType.name()),
        email,
        (String) kakaoAttributes.get(providerType.NICKNAME_FIELD),
        (String) kakaoAttributes.get(providerType.IMAGE_FILED),
        providerId
    );
  }

  private static OAuth2UserAttributes fromNaver
      (OAuth2ProviderType providerType, Map<String, Object> attributes) {
    Map<String, Object> naverAttributes = extractAttributes(providerType.ATTRIBUTES_FIELD, attributes);
    return new OAuth2UserAttributes(
        attributes,
        ProviderType.find(providerType.name()),
        (String) naverAttributes.get(providerType.EMAIL_FIELD),
        (String) naverAttributes.get(providerType.NICKNAME_FIELD),
        (String) naverAttributes.get(providerType.IMAGE_FILED),
        null
    );
  }

  private static OAuth2UserAttributes fromGoogle
      (OAuth2ProviderType providerType, Map<String, Object> attributes) {
    return new OAuth2UserAttributes(
        attributes,
        ProviderType.find(providerType.name()),
        (String) attributes.get(providerType.EMAIL_FIELD),
        (String) attributes.get(providerType.NICKNAME_FIELD),
        (String) attributes.get(providerType.IMAGE_FILED),
        null
    );
  }

  @SuppressWarnings("unchecked")
  private static Map<String, Object> extractAttributes(String ATTRIBUTES_FIELD, Map<String, Object> attributes) {
    return (Map<String, Object>) attributes.get(ATTRIBUTES_FIELD);
  }

  public Account toAccount(String password) {
    return Account.builder()
                  .email(email)
                  .nickname(nickname)
                  .profileImage(image)
                  .password(password)
                  .providerType(providerType)
                  .providerId(providerId)
                  .build();
  }

}