package com.hiddenartist.backend.domain.account.controller;

import com.hiddenartist.backend.domain.account.controller.request.AccountDeleteFollowArtistRequest;
import com.hiddenartist.backend.domain.account.controller.request.AccountUpdateImageRequest;
import com.hiddenartist.backend.domain.account.controller.request.AccountUpdateNicknameRequest;
import com.hiddenartist.backend.domain.account.controller.response.AccountGetDetailResponse;
import com.hiddenartist.backend.domain.account.controller.response.AccountGetMentoringApplicationResponse;
import com.hiddenartist.backend.domain.account.controller.response.AccountGetSimpleResponse;
import com.hiddenartist.backend.domain.account.controller.response.FollowArtistGetListResponse;
import com.hiddenartist.backend.domain.account.service.AccountService;
import com.hiddenartist.backend.global.type.CookieNames;
import com.hiddenartist.backend.global.utils.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

  private static final String REDIRECT_URL = "/oauth2/authorization/";
  private final AccountService accountService;

  @GetMapping("/signin/{provider}")
  public void redirectOAuth2LoginPage(@PathVariable String provider, HttpServletResponse response) throws IOException {
    String redirectUrl = REDIRECT_URL + provider;
    response.sendRedirect(redirectUrl);
  }

  @DeleteMapping("/withdraw")
  public void removeAccountWithdraw(@AuthenticationPrincipal String email,
      HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = CookieManager.getCookie(CookieNames.REFRESH_TOKEN.getName(), request);
    accountService.withDrawAccount(email, refreshToken);
    CookieManager.removeCookie(CookieNames.ACCESS_TOKEN.getName(), response);
    CookieManager.removeCookie(CookieNames.REFRESH_TOKEN.getName(), response);
  }

  @GetMapping("/me/profile")
  public AccountGetSimpleResponse getAccountSimpleProfile(@AuthenticationPrincipal String email) {
    return accountService.getAccountSimpleInfo(email);
  }

  @GetMapping("/me")
  public AccountGetDetailResponse getAccountDetailProfile(@AuthenticationPrincipal String email) {
    return accountService.getAccountDetailInfo(email);
  }

  @PatchMapping("/me/nickname")
  public void updateAccountNickname(@RequestBody AccountUpdateNicknameRequest nicknameRequest,
      @AuthenticationPrincipal String email) {
    accountService.updateAccountNickname(email, nicknameRequest.nickname());
  }

  @PatchMapping("/me/image")
  public void updateAccountImage(@RequestBody AccountUpdateImageRequest imageRequest,
      @AuthenticationPrincipal String email) {
    accountService.updateAccountImage(email, imageRequest.profileImage());
  }

  @GetMapping("/me/artists")
  public FollowArtistGetListResponse getFollowArtists(@AuthenticationPrincipal String email) {
    return accountService.getFollowArtists(email);
  }

  @DeleteMapping("/me/artists")
  public void deleteFollowArtists(@RequestBody AccountDeleteFollowArtistRequest deleteFollowArtistRequest,
      @AuthenticationPrincipal String email) {
    accountService.deleteFollowArtists(email, deleteFollowArtistRequest.artists());
  }

  @GetMapping("/me/mentorings")
  public Page<AccountGetMentoringApplicationResponse> getMentoringApplications(
      @PageableDefault(size = 20, page = 1, sort = "applicationTime", direction = Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal String email
  ) {
    return accountService.getMentoringApplications(pageable, email);
  }

}