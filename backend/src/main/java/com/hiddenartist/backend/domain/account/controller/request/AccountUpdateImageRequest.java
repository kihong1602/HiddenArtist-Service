package com.hiddenartist.backend.domain.account.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountUpdateImageRequest(
    @JsonProperty("profile_image")
    String profileImage
) {

}
