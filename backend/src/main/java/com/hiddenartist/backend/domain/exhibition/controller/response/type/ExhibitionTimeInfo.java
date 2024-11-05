package com.hiddenartist.backend.domain.exhibition.controller.response.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;

public record ExhibitionTimeInfo(
    @JsonProperty("open_time")
    @JsonFormat(shape = Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    LocalTime openTime,

    @JsonProperty("close_time")
    @JsonFormat(shape = Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    LocalTime closeTime
) {

}