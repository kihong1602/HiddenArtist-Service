package com.hiddenartist.backend.domain.mentoring.persistence.repository;

import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringRepository extends JpaRepository<Mentoring, Long>, CustomMentoringRepository {

}
