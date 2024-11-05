package com.hiddenartist.backend.domain.exhibition.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.exhibition.controller.response.type.ExhibitionPeriodInfo;
import com.hiddenartist.backend.domain.exhibition.controller.response.type.ExhibitionTimeInfo;
import com.hiddenartist.backend.domain.exhibition.controller.response.type.LocationInfo;
import com.hiddenartist.backend.domain.exhibition.controller.response.type.ManagerInfo;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.domain.exhibition.persistence.ExhibitionLocation;
import com.hiddenartist.backend.domain.exhibition.persistence.ExhibitionManager;
import lombok.Getter;

@Getter
public class ExhibitionDetailResponse extends ExhibitionResponse {

  private String description;

  private ExhibitionPeriodInfo period;

  private ExhibitionTimeInfo time;

  @JsonProperty("closed_days")
  private String closedDays;

  @JsonProperty("admission_fee")
  private Integer admissionFee;

  private LocationInfo location;

  private ManagerInfo manager;

  private ExhibitionDetailResponse(String name, String token, String image, String description, ExhibitionPeriodInfo period,
      ExhibitionTimeInfo time, String closedDays, Integer admissionFee,
      LocationInfo location, ManagerInfo manager) {
    super(name, token, image);
    this.description = description;
    this.period = period;
    this.time = time;
    this.closedDays = closedDays;
    this.admissionFee = admissionFee;
    this.location = location;
    this.manager = manager;
  }

  public static ExhibitionDetailResponse convert(Exhibition exhibition) {
    ExhibitionPeriodInfo period = new ExhibitionPeriodInfo(exhibition.getStartDate(), exhibition.getEndDate());
    ExhibitionTimeInfo time = new ExhibitionTimeInfo(exhibition.getOpenTime(), exhibition.getCloseTime());
    ExhibitionLocation location = exhibition.getLocation();
    LocationInfo locationInfo = new LocationInfo(location.getName(), location.getAddress(), location.getLatitude(),
        location.getLongitude());
    ExhibitionManager manager = exhibition.getManager();
    ManagerInfo managerInfo = new ManagerInfo(manager.getManagerName(), manager.getEmail(), manager.getTel());
    return new ExhibitionDetailResponse(exhibition.getName(), exhibition.getToken(), exhibition.getImage(),
        exhibition.getDescription(), period, time, exhibition.getClosedDays(), exhibition.getAdmissionFee(), locationInfo,
        managerInfo
    );
  }
}
