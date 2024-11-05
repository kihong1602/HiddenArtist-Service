package com.hiddenartist.backend.domain.exhibition.persistence;

import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExhibitionLocation extends BaseEntity {

  private String name;

  private String address;

  private Double latitude;

  private Double longitude;
}