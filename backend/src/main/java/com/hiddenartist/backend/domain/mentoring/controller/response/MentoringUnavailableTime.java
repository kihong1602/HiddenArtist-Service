package com.hiddenartist.backend.domain.mentoring.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record MentoringUnavailableTime(
    @JsonProperty("unavailable_times")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    List<LocalDateTime> unavailableTimes
) {

  public static MentoringUnavailableTime create(List<LocalDateTime> reservationTimes, List<LocalDateTime> applicationTimes) {
    List<LocalDateTime> unavailableTimes = new ArrayList<>();
    unavailableTimes.addAll(reservationTimes);
    unavailableTimes.addAll(applicationTimes);
    unavailableTimes.sort(LocalDateTime::compareTo);
    return new MentoringUnavailableTime(unavailableTimes);
  }

}