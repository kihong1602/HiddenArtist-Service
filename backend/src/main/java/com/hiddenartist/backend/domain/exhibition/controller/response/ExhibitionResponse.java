package com.hiddenartist.backend.domain.exhibition.controller.response;

import com.hiddenartist.backend.global.type.EntityToken;
import lombok.Getter;

@Getter
public abstract class ExhibitionResponse {

  private String name;
  private String token;
  private String image;

  protected ExhibitionResponse(String name, String token, String image) {
    this.name = name;
    this.token = EntityToken.EXHIBITION.extractToken(token);
    this.image = image;
  }
}