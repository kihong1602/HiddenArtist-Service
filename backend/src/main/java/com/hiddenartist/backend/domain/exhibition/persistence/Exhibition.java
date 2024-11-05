package com.hiddenartist.backend.domain.exhibition.persistence;

import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exhibition extends BaseEntity {

  private String name;

  private String image;

  @Column(columnDefinition = "mediumtext")
  private String description;

  private LocalDate startDate;

  private LocalDate endDate;

  private LocalTime openTime;

  private LocalTime closeTime;

  private String closedDays;

  private Integer admissionFee;

  private String token;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExhibitionLocation location;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExhibitionManager manager;

}