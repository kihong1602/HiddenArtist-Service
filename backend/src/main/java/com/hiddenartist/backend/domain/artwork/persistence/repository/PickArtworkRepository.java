package com.hiddenartist.backend.domain.artwork.persistence.repository;

import com.hiddenartist.backend.domain.artwork.persistence.PickArtwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickArtworkRepository extends JpaRepository<PickArtwork, Long> {

}