package com.hiddenartist.backend.domain.mentoring.persistence;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringApplicationStatus;
import com.hiddenartist.backend.global.converter.MentoringApplicationStatusConverter;
import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentoringApplication extends BaseEntity {

  private String token;

  private LocalDateTime applicationTime;

  @Convert(converter = MentoringApplicationStatusConverter.class)
  private MentoringApplicationStatus mentoringApplicationStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  private Mentoring mentoring;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  public MentoringApplication(String token, LocalDateTime applicationTime, MentoringApplicationStatus mentoringApplicationStatus,
      Mentoring mentoring, Account account) {
    this.token = token;
    this.applicationTime = applicationTime;
    this.mentoringApplicationStatus = mentoringApplicationStatus;
    this.mentoring = mentoring;
    this.account = account;
  }

}