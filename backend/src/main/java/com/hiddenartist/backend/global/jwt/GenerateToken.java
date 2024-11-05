package com.hiddenartist.backend.global.jwt;

public record GenerateToken(
    String accessToken,
    RefreshToken refreshToken
) {

}
