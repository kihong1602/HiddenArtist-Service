package com.hiddenartist.backend.domain.admin.service;

import com.hiddenartist.backend.domain.genre.persistence.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminGenreService {

  private final GenreRepository genreRepository;
}