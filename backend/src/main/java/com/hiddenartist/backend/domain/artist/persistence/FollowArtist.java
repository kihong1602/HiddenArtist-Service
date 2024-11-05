package com.hiddenartist.backend.domain.artist.persistence;

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
public class FollowArtist extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  private Artist artist;

  private FollowArtist(Account account, Artist artist) {
    this.account = account;
    this.artist = artist;
  }

}