package com.hiddenartist.backend.domain.exhibition.controller.response;

import org.springframework.data.domain.Page;

public record ExhibitionGetPageResponse(
    Page<ExhibitionSimpleResponse> exhibitions
) {

}