package com.hiddenartist.backend.domain.mentor.persistence;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.mentor.persistence.type.Career;
import com.hiddenartist.backend.domain.mentor.persistence.type.CertificationStatus;
import com.hiddenartist.backend.global.converter.CareerConverter;
import com.hiddenartist.backend.global.converter.CertificationStatusConverter;
import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentor extends BaseEntity {

  private String summary;

  private String organization;

  @Default
  @Convert(converter = CareerConverter.class)
  private Career career = Career.JUNIOR;

  private String bankName;

  private String accountName;

  private String accountNumber;

  private String contactEmail;

  @Default
  @Convert(converter = CertificationStatusConverter.class)
  private CertificationStatus certificationStatus = CertificationStatus.UNVERIFIED;

  @OneToOne(fetch = FetchType.LAZY)
  private Account account;

  private Mentor(String summary, String organization, Career career, String bankName, String accountName, String accountNumber,
      String contactEmail, CertificationStatus certificationStatus, Account account) {
    this.summary = summary;
    this.organization = organization;
    this.career = career;
    this.bankName = bankName;
    this.accountName = accountName;
    this.accountNumber = accountNumber;
    this.contactEmail = contactEmail;
    this.certificationStatus = certificationStatus;
    this.account = account;
  }

}