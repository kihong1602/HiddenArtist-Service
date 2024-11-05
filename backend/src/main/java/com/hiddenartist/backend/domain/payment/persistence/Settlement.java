package com.hiddenartist.backend.domain.payment.persistence;

import com.hiddenartist.backend.domain.mentor.persistence.Mentor;
import com.hiddenartist.backend.domain.payment.persistence.type.SettlementStatus;
import com.hiddenartist.backend.global.converter.SettlementStatusConverter;
import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement extends BaseEntity {

  private LocalDateTime settlementDate;

  @Convert(converter = SettlementStatusConverter.class)
  private SettlementStatus settlementStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  private Mentor mentor;

  @OneToOne(fetch = FetchType.LAZY)
  private Payment payment;
}