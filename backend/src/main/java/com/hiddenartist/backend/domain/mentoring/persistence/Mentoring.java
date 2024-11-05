package com.hiddenartist.backend.domain.mentoring.persistence;

import com.hiddenartist.backend.domain.mentor.persistence.Mentor;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringStatus;
import com.hiddenartist.backend.global.converter.MentoringStatusConverter;
import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentoring extends BaseEntity {

  private String name;

  private String image;

  @Column(columnDefinition = "mediumtext")
  private String content;

  private String durationTime;

  private Integer amount;

  private Long totalApplicationCount;

  private String token;

  @Convert(converter = MentoringStatusConverter.class)
  private MentoringStatus mentoringStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  private Mentor mentor;

  private Mentoring(String name, String image, String content, String durationTime, Integer amount, Long totalApplicationCount,
      String token, MentoringStatus mentoringStatus, Mentor mentor) {
    this.name = name;
    this.image = image;
    this.content = content;
    this.durationTime = durationTime;
    this.amount = amount;
    this.totalApplicationCount = totalApplicationCount;
    this.token = token;
    this.mentoringStatus = mentoringStatus;
    this.mentor = mentor;
  }
}