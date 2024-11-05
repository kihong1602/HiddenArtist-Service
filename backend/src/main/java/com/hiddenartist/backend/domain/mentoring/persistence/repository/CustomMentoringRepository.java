package com.hiddenartist.backend.domain.mentoring.persistence.repository;

import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringDetailResponse;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.mentoring.persistence.MentoringApplication;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomMentoringRepository {

  Page<Mentoring> findAllMentorings(Pageable pageable);

  MentoringDetailResponse findMentoringByToken(String token);

  List<MentoringApplication> findMentoringApplicationByMonth(String token, LocalDate selectMonth);

}