package com.hiddenartist.backend.domain.artist.controller.response;

import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkSimpleResponse;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import java.util.List;

public record ArtistGetAllArtworkResponse(
    List<ArtworkSimpleResponse> artworks
) {

  public static ArtistGetAllArtworkResponse create(List<Artwork> artworks) {
    List<ArtworkSimpleResponse> simpleArtworkResponses = artworks.stream().map(ArtworkSimpleResponse::convert).toList();
    return new ArtistGetAllArtworkResponse(simpleArtworkResponses);
  }

}