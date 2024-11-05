package com.hiddenartist.backend.domain.admin.controller;

import com.hiddenartist.backend.domain.admin.controller.request.AdminSignUpInfo;
import com.hiddenartist.backend.domain.admin.service.AdminAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/accounts")
@RequiredArgsConstructor
public class AdminAccountController {

  private final AdminAccountService accountService;

  @PostMapping("/signup")
  public String addAdminUser(@RequestBody AdminSignUpInfo signUpInfo) {
    accountService.saveAdminAccount(signUpInfo);
    return "SUCCESS";
  }

}