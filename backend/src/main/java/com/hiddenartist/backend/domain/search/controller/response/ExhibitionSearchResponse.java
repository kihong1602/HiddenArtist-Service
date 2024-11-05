package com.hiddenartist.backend.domain.search.controller.response;

import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionResponse;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;

public class ExhibitionSearchResponse extends ExhibitionResponse {

  private ExhibitionSearchResponse(String name, String token, String image) {
    super(name, token, image);
  }

  public static ExhibitionSearchResponse create(Exhibition exhibition) {
    return new ExhibitionSearchResponse(exhibition.getName(), exhibition.getToken(), exhibition.getImage());
  }

}