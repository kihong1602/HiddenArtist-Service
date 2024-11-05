package com.hiddenartist.backend.domain.mentor.service;

import com.hiddenartist.backend.domain.mentor.controller.response.ReceivedMentoringApplicationResponse;
import com.hiddenartist.backend.domain.mentor.persistence.repository.MentorRepository;
import com.hiddenartist.backend.global.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MentorService {

  private final MentorRepository mentorRepository;

  @Transactional(readOnly = true)
  public Page<ReceivedMentoringApplicationResponse> getReceivedMentoringApplications(Pageable pageable, String email) {
    Pageable pageRequest = PageUtils.createPageRequest(pageable);
    return mentorRepository.getReceivedMentoringApplications(pageRequest, email);
  }

}