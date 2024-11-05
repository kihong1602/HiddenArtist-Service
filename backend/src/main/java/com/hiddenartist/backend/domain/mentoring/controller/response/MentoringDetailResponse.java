package com.hiddenartist.backend.domain.mentoring.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.domain.mentor.persistence.Mentor;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.mentoring.persistence.MentoringReview;
import java.util.List;
import lombok.Getter;

@Getter
public class MentoringDetailResponse extends MentoringResponse {

  private String content;

  private Integer amount;

  @JsonProperty("duration_time")
  private String durationTime;

  private List<String> genres;

  private MentorResponse mentor;

  private List<ReviewInfo> reviews;

  private MentoringDetailResponse(String name, String token, String image, String content, Integer amount, String durationTime,
      List<String> genres, MentorResponse mentorResponse, List<ReviewInfo> reviews) {
    super(name, token, image);
    this.content = content;
    this.amount = amount;
    this.durationTime = durationTime;
    this.genres = genres;
    this.mentor = mentorResponse;
    this.reviews = reviews;
  }

  public static MentoringDetailResponse create(Mentoring mentoring, List<Genre> genres, List<MentoringReview> reviews) {
    Mentor mentor = mentoring.getMentor();
    MentorResponse mentorResponse = MentorResponse.create(mentor);
    List<String> genreNames = genres.stream().map(Genre::getName).toList();
    List<ReviewInfo> reviewInfos = reviews.stream()
                                          .map(review -> new ReviewInfo(review.getRating(), review.getContent()))
                                          .toList();
    return new MentoringDetailResponse(
        mentoring.getName(),
        mentoring.getToken(),
        mentoring.getImage(),
        mentoring.getContent(),
        mentoring.getAmount(),
        mentoring.getDurationTime(),
        genreNames,
        mentorResponse,
        reviewInfos
    );
  }

  public record ReviewInfo(
      Integer rating,

      String content
  ) {

  }

}