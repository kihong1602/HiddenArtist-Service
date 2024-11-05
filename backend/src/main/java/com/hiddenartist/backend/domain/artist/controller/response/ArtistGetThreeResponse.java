package com.hiddenartist.backend.domain.artist.controller.response;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import java.util.List;

public record ArtistGetThreeResponse(
    List<ArtistSimpleResponse> artists
) {

  public static ArtistGetThreeResponse create(List<Artist> artists) {
    List<ArtistSimpleResponse> simpleResponses = artists.stream().map(ArtistSimpleResponse::convert).toList();
    return new ArtistGetThreeResponse(simpleResponses);
  }

}