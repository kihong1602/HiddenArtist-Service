package com.hiddenartist.backend.domain.admin.controller.request;

public record AdminSignUpInfo(
    String email,
    String password,
    String nickname
) {

}