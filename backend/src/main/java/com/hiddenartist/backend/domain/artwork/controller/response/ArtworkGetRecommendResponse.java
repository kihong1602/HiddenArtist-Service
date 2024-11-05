package com.hiddenartist.backend.domain.artwork.controller.response;

import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import java.util.List;

public record ArtworkGetRecommendResponse(
    List<ArtworkSimpleResponse> artworks
) {

  public static ArtworkGetRecommendResponse create(List<Artwork> artworks) {
    List<ArtworkSimpleResponse> simpleArtworkResponses = artworks.stream().map(ArtworkSimpleResponse::convert).toList();
    return new ArtworkGetRecommendResponse(simpleArtworkResponses);
  }

}