package com.hiddenartist.backend.domain.exhibition.persistence;

import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExhibitionManager extends BaseEntity {

  private String managerName;

  private String email;

  private String tel;

}