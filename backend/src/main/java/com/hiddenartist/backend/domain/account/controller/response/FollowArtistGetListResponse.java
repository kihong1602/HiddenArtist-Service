package com.hiddenartist.backend.domain.account.controller.response;

import com.hiddenartist.backend.domain.artist.controller.response.ArtistSimpleResponse;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import java.util.List;

public record FollowArtistGetListResponse(
    List<ArtistSimpleResponse> artists
) {

  public static FollowArtistGetListResponse convert(List<Artist> artists) {
    List<ArtistSimpleResponse> artistSimpleResponse = artists.stream().map(ArtistSimpleResponse::convert).toList();
    return new FollowArtistGetListResponse(artistSimpleResponse);
  }
}