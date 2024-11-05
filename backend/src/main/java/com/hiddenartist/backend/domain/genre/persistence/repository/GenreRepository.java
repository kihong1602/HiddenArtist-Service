package com.hiddenartist.backend.domain.genre.persistence.repository;

import com.hiddenartist.backend.domain.genre.persistence.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
