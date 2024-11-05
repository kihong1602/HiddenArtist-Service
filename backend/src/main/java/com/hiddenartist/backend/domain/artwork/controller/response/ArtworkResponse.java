package com.hiddenartist.backend.domain.artwork.controller.response;

import com.hiddenartist.backend.global.type.EntityToken;
import lombok.Getter;

@Getter
public abstract class ArtworkResponse {

  private String name;
  private String token;
  private String image;

  protected ArtworkResponse(String name, String token, String image) {
    this.name = name;
    this.token = EntityToken.ARTWORK.extractToken(token);
    this.image = image;
  }

}