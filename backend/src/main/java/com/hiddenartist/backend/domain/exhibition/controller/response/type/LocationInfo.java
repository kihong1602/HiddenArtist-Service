package com.hiddenartist.backend.domain.exhibition.controller.response.type;

public record LocationInfo(
    String name,

    String address,

    Double latitude,

    Double longitude
) {

}
