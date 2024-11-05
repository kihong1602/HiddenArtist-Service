package com.hiddenartist.backend.domain.mentoring.controller.response;

import com.hiddenartist.backend.domain.mentor.persistence.Mentor;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import lombok.Getter;

@Getter
public class MentoringSimpleResponse extends MentoringResponse {

  private Integer amount;

  private MentorResponse mentor;

  private MentoringSimpleResponse(String name, String token, String image, Integer amount, MentorResponse mentor) {
    super(name, token, image);
    this.amount = amount;
    this.mentor = mentor;
  }

  public static MentoringSimpleResponse create(Mentoring mentoring) {
    Mentor mentor = mentoring.getMentor();
    MentorResponse mentorResponse = MentorResponse.create(mentor);
    return new MentoringSimpleResponse(mentoring.getName(), mentoring.getToken(), mentoring.getImage(), mentoring.getAmount(),
        mentorResponse);
  }

}
