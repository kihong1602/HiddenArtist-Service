package com.hiddenartist.backend.domain.mentoring.persistence;

import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentoringReview extends BaseEntity {

  private String content;

  private Integer rating;

  @ManyToOne(fetch = FetchType.LAZY)
  private MentoringApplication mentoringApplication;

  @ManyToOne(fetch = FetchType.LAZY)
  private Mentoring mentoring;

}