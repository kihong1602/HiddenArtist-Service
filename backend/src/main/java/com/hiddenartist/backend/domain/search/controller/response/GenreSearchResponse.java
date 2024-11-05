package com.hiddenartist.backend.domain.search.controller.response;

import com.hiddenartist.backend.domain.genre.persistence.Genre;

public record GenreSearchResponse(
    String name
) {

  public static GenreSearchResponse create(Genre genre) {
    return new GenreSearchResponse(genre.getName());
  }

}