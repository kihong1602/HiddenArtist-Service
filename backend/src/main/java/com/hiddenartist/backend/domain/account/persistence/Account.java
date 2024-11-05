package com.hiddenartist.backend.domain.account.persistence;

import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import com.hiddenartist.backend.domain.account.persistence.type.Role;
import com.hiddenartist.backend.global.converter.ProviderTypeConverter;
import com.hiddenartist.backend.global.converter.RoleConverter;
import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

  private String email;

  private String password;

  private String nickname;

  private String profileImage;

  @Default
  @Convert(converter = ProviderTypeConverter.class)
  private ProviderType providerType = ProviderType.LOCAL;

  private Long providerId;

  @Default
  @Convert(converter = RoleConverter.class)
  private Role role = Role.USER;

  private Account(String email, String password, String nickname, String profileImage, ProviderType providerType,
      Long providerId, Role role) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.profileImage = profileImage;
    this.providerType = providerType;
    this.providerId = providerId;
    this.role = role;
  }

  public String getRoleKey() {
    return role.getKey();
  }

  public void updateNickname(String nickname) {
    this.nickname = nickname;
  }

  public void updateProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

}