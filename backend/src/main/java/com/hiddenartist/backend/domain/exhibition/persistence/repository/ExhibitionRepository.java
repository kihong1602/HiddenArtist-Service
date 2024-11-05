package com.hiddenartist.backend.domain.exhibition.persistence.repository;

import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long>, CustomExhibitionRepository {

}
