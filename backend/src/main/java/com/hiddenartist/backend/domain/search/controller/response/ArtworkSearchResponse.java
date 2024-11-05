package com.hiddenartist.backend.domain.search.controller.response;

import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkResponse;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;

public class ArtworkSearchResponse extends ArtworkResponse {

  private ArtworkSearchResponse(String name, String token, String image) {
    super(name, token, image);
  }

  public static ArtworkSearchResponse create(Artwork artwork) {
    return new ArtworkSearchResponse(artwork.getName(), artwork.getToken(), artwork.getImage());
  }

}