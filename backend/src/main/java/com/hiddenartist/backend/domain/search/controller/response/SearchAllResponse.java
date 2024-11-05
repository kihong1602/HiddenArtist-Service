package com.hiddenartist.backend.domain.search.controller.response;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import java.util.List;

public record SearchAllResponse(
    List<ArtistSearchResponse> artists,
    List<ArtworkSearchResponse> artworks,
    List<ExhibitionSearchResponse> exhibitions
) {

  public static SearchAllResponse create(List<Artist> artists, List<Artwork> artworks, List<Exhibition> exhibitions) {
    List<ArtistSearchResponse> artistSearchResponses = artists.stream().map(ArtistSearchResponse::create).toList();
    List<ArtworkSearchResponse> artworkSearchResponses = artworks.stream().map(ArtworkSearchResponse::create).toList();
    List<ExhibitionSearchResponse> exhibitionSearchResponses = exhibitions.stream()
                                                                          .map(ExhibitionSearchResponse::create)
                                                                          .toList();
    return new SearchAllResponse(artistSearchResponses, artworkSearchResponses, exhibitionSearchResponses);
  }
  
}