package com.hiddenartist.backend.domain.genre.persistence;

import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
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
public class MentoringGenre extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Mentoring mentoring;

  @ManyToOne(fetch = FetchType.LAZY)
  private Genre genre;

}