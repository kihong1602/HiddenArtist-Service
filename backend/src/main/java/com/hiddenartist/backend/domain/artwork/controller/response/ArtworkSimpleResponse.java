package com.hiddenartist.backend.domain.artwork.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ArtworkSimpleResponse extends ArtworkResponse {

  @JsonProperty("production_year")
  private Integer productionYear;

  private ArtworkSimpleResponse(String name, String token, String image, LocalDate productionYear) {
    super(name, token, image);
    this.productionYear = productionYear.getYear();
  }

  public static ArtworkSimpleResponse convert(Artwork artwork) {
    return new ArtworkSimpleResponse(artwork.getName(), artwork.getToken(), artwork.getImage(),
        artwork.getProductionYear());
  }
}