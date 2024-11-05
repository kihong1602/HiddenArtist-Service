package com.hiddenartist.backend.global.security.auth;

import com.hiddenartist.backend.domain.account.persistence.Account;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PrincipalDetails implements OAuth2User, UserDetails {

  private SecurityUserInfo userInfo;
  private Map<String, Object> attributes;
  private Collection<? extends GrantedAuthority> authorities;

  private PrincipalDetails(SecurityUserInfo userInfo, Collection<? extends GrantedAuthority> authorities) {
    this.userInfo = userInfo;
    this.authorities = authorities;
  }

  public static PrincipalDetails create(Account account, Map<String, Object> attributes) {
    String roleKey = account.getRoleKey();
    Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(roleKey));
    SecurityUserInfo userInfo = SecurityUserInfo.convert(account);
    return new PrincipalDetails(userInfo, attributes, authorities);
  }

  public static PrincipalDetails create(Account account) {
    String roleKey = account.getRoleKey();
    Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(roleKey));
    SecurityUserInfo userInfo = SecurityUserInfo.convert(account);
    return new PrincipalDetails(userInfo, authorities);
  }

  // OAuth2 User
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  // OAuth2 User
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  // UserDetails
  @Override
  public String getPassword() {
    return userInfo.password();
  }

  // UserDetails
  @Override
  public String getUsername() {
    return userInfo.email();
  }

  // OAuth2 User
  @Override
  public String getName() {
    return userInfo.nickname();
  }
}
