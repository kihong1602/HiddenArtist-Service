package com.hiddenartist.backend.domain.genre.persistence;

import com.hiddenartist.backend.domain.mentor.persistence.Mentor;
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
public class MentorGenre extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Mentor mentor;

  @ManyToOne(fetch = FetchType.LAZY)
  private Genre genre;

}