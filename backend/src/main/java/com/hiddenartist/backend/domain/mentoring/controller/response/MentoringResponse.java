package com.hiddenartist.backend.domain.mentoring.controller.response;

import com.hiddenartist.backend.global.type.EntityToken;
import lombok.Getter;

@Getter
public abstract class MentoringResponse {

  private String name;

  private String token;

  private String image;

  protected MentoringResponse(String name, String token, String image) {
    this.name = name;
    this.token = EntityToken.MENTORING.extractToken(token);
    this.image = image;
  }

}