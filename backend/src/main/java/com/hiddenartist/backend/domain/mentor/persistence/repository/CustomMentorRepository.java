package com.hiddenartist.backend.domain.mentor.persistence.repository;

import com.hiddenartist.backend.domain.mentor.controller.response.ReceivedMentoringApplicationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomMentorRepository {

  Page<ReceivedMentoringApplicationResponse> getReceivedMentoringApplications(Pageable pageable, String email);

}