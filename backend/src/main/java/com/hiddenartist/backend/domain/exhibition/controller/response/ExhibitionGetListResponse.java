package com.hiddenartist.backend.domain.exhibition.controller.response;

import java.util.List;

public record ExhibitionGetListResponse(
    List<ExhibitionSimpleResponse> exhibitions
) {

}
