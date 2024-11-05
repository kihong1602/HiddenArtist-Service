package com.hiddenartist.backend.domain.exhibition.controller.response;

import com.hiddenartist.backend.domain.exhibition.controller.response.type.ExhibitionPeriodInfo;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import lombok.Getter;

@Getter
public class ExhibitionSimpleResponse extends ExhibitionResponse {

  private ExhibitionPeriodInfo period;

  public ExhibitionSimpleResponse(String name, String token, String image, ExhibitionPeriodInfo period) {
    super(name, token, image);
    this.period = period;
  }

  public static ExhibitionSimpleResponse convert(Exhibition exhibition) {
    ExhibitionPeriodInfo period = new ExhibitionPeriodInfo(exhibition.getStartDate(), exhibition.getEndDate());
    return new ExhibitionSimpleResponse(exhibition.getName(), exhibition.getToken(), exhibition.getImage(), period);
  }

}