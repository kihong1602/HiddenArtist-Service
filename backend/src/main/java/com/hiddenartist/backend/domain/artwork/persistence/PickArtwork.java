package com.hiddenartist.backend.domain.artwork.persistence;

import com.hiddenartist.backend.domain.account.persistence.Account;
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
public class PickArtwork extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  private Artwork artwork;

  private PickArtwork(Account account, Artwork artwork) {
    this.account = account;
    this.artwork = artwork;
  }

}