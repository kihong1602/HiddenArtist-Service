package com.hiddenartist.backend.domain.genre.persistence;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
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
public class ArtistGenre extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Artist artist;

  @ManyToOne(fetch = FetchType.LAZY)
  private Genre genre;

  private ArtistGenre(Artist artist, Genre genre) {
    this.artist = artist;
    this.genre = genre;
  }

}