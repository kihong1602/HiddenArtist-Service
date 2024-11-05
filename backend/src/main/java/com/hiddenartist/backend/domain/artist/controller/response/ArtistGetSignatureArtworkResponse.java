package com.hiddenartist.backend.domain.artist.controller.response;

import java.util.List;

public record ArtistGetSignatureArtworkResponse(
    List<SignatureArtworkResponse> artworks
) {

}