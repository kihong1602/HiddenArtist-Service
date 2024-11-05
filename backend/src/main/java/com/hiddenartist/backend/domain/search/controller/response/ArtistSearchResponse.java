package com.hiddenartist.backend.domain.search.controller.response;

import com.hiddenartist.backend.domain.artist.controller.response.ArtistResponse;
import com.hiddenartist.backend.domain.artist.persistence.Artist;

public class ArtistSearchResponse extends ArtistResponse {

  private ArtistSearchResponse(String name, String token, String image) {
    super(name, token, image);
  }

  public static ArtistSearchResponse create(Artist artist) {
    return new ArtistSearchResponse(artist.getName(), artist.getToken(), artist.getProfileImage());
  }

}