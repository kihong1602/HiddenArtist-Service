package com.hiddenartist.backend.domain.artist.controller.response;

import org.springframework.data.domain.Page;

public record ArtistGetListResponse(
    Page<ArtistSimpleResponse> artists
) {

}