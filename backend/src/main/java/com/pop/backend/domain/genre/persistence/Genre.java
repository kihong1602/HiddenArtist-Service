package com.pop.backend.domain.genre.persistence;

import com.pop.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Genre extends BaseEntity {

  private String name;

  public Genre(String name) {
    this.name = name;
  }

}