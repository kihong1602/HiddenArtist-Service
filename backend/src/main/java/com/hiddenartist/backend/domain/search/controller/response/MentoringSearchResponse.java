package com.hiddenartist.backend.domain.search.controller.response;

import com.hiddenartist.backend.domain.mentor.persistence.Mentor;
import com.hiddenartist.backend.domain.mentoring.controller.response.MentorResponse;
import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringResponse;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import lombok.Getter;

@Getter
public class MentoringSearchResponse extends MentoringResponse {

  private MentorResponse mentor;

  private MentoringSearchResponse(String name, String token, String image, MentorResponse mentor) {
    super(name, token, image);
    this.mentor = mentor;
  }

  public static MentoringSearchResponse create(Mentoring mentoring) {
    Mentor mentor = mentoring.getMentor();
    MentorResponse mentorResponse = MentorResponse.create(mentor);
    return new MentoringSearchResponse(mentoring.getName(), mentoring.getToken(), mentoring.getImage(), mentorResponse);
  }

}