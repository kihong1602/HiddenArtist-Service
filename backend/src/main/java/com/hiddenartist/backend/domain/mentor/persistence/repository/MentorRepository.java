package com.hiddenartist.backend.domain.mentor.persistence.repository;

import com.hiddenartist.backend.domain.mentor.persistence.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Long>, CustomMentorRepository {

}