package com.hiddenartist.backend.domain.artist.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkResponse;
import lombok.Getter;

@Getter
public class SignatureArtworkResponse extends ArtworkResponse {

  @JsonProperty("display_order")
  private Byte displayOrder;

  public SignatureArtworkResponse(String name, String token, String image, Byte displayOrder) {
    super(name, token, image);
    this.displayOrder = displayOrder;
  }

}
