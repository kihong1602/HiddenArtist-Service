package com.hiddenartist.backend.domain.artist.controller.response;

import com.hiddenartist.backend.global.type.EntityToken;
import java.util.Objects;
import lombok.Getter;

@Getter
public abstract class ArtistResponse {

  private String name;
  private String token;
  private String image;

  protected ArtistResponse(String name, String token, String image) {
    this.name = name;
    this.token = EntityToken.ARTIST.extractToken(token);
    this.image = image;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ArtistResponse that)) {
      return false;
    }
    return Objects.equals(getName(), that.getName()) && Objects.equals(getToken(), that.getToken())
        && Objects.equals(getImage(), that.getImage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getToken(), getImage());
  }

}