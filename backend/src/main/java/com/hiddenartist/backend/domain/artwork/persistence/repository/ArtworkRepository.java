package com.hiddenartist.backend.domain.artwork.persistence.repository;

import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtworkRepository extends JpaRepository<Artwork, Long>, CustomArtworkRepository {

  Optional<Artwork> findByToken(String token);

}