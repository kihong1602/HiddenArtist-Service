package com.hiddenartist.backend.domain.artwork.persistence;

import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignatureArtwork extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Artwork artwork;

  private Byte displayOrder;

  private SignatureArtwork(Artwork artwork, Byte displayOrder) {
    this.artwork = artwork;
    this.displayOrder = displayOrder;
  }

}