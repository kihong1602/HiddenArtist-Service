package com.hiddenartist.backend.domain.artwork.persistence;

import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Artwork extends BaseEntity {

  private String name;

  private String image;

  private String description;

  private LocalDate productionYear;

  private Double width;

  private Double height;

  private Double depth;

  private String token;

  @ManyToOne(fetch = FetchType.LAZY)
  private ArtworkMedium artworkMedium;

  private Artwork(String name, String image, String description, LocalDate productionYear, Double width, Double height,
      Double depth, String token, ArtworkMedium artworkMedium) {
    this.name = name;
    this.image = image;
    this.description = description;
    this.productionYear = productionYear;
    this.width = width;
    this.height = height;
    this.depth = depth;
    this.token = token;
    this.artworkMedium = artworkMedium;
  }

}