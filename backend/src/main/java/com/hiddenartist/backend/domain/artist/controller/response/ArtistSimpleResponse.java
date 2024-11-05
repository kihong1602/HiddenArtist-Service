package com.hiddenartist.backend.domain.artist.controller.response;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import java.util.Objects;
import lombok.Getter;

@Getter
public class ArtistSimpleResponse extends ArtistResponse {

  private String summary;

  public ArtistSimpleResponse(String name, String token, String image, String summary) {
    super(name, token, image);
    this.summary = summary;
  }

  public static ArtistSimpleResponse convert(Artist artist) {
    return new ArtistSimpleResponse(artist.getName(), artist.getToken(), artist.getProfileImage(), artist.getSummary());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ArtistSimpleResponse that)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    return Objects.equals(getSummary(), that.getSummary());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getSummary());
  }
}