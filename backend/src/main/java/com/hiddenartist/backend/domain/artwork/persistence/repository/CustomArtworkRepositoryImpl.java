package com.hiddenartist.backend.domain.artwork.persistence.repository;

import static com.hiddenartist.backend.domain.artist.persistence.QArtist.artist;
import static com.hiddenartist.backend.domain.artist.persistence.QArtistArtwork.artistArtwork;
import static com.hiddenartist.backend.domain.artwork.persistence.QArtwork.artwork;
import static com.hiddenartist.backend.domain.artwork.persistence.QArtworkMedium.artworkMedium;
import static com.hiddenartist.backend.domain.genre.persistence.QArtworkGenre.artworkGenre;
import static com.hiddenartist.backend.domain.genre.persistence.QGenre.genre;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkDetailResponse;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.global.exception.type.EntityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomArtworkRepositoryImpl implements CustomArtworkRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public ArtworkDetailResponse findArtworkDetailByToken(String token) {
    Artwork findArtwork = Optional.ofNullable(queryFactory.selectFrom(artwork)
                                                          .leftJoin(artwork.artworkMedium, artworkMedium)
                                                          .fetchJoin()
                                                          .where(artwork.token.eq(token))
                                                          .fetchOne())
                                  .orElseThrow(() -> new EntityException(ServiceErrorCode.ARTWORK_NOT_FOUND));

    List<Artist> artists = queryFactory.select(artist)
                                       .from(artistArtwork)
                                       .join(artistArtwork.artist, artist)
                                       .where(artistArtwork.artwork.eq(findArtwork))
                                       .orderBy(artist.name.asc())
                                       .fetch();

    List<Genre> genres = queryFactory.select(genre)
                                     .from(artworkGenre)
                                     .join(artworkGenre.genre, genre)
                                     .where(artworkGenre.artwork.eq(findArtwork))
                                     .orderBy(genre.name.asc())
                                     .fetch();
    return ArtworkDetailResponse.create(findArtwork, artists, genres);
  }

  @Override
  public List<Artwork> findArtworkRecommend() {
    Long totalCount = queryFactory.select(artwork.count()).from(artwork).fetchOne();
    if (totalCount < 3) {
      return queryFactory.selectFrom(artwork).fetch();
    }

    return queryFactory.selectFrom(artwork)
                       .orderBy(Expressions.numberTemplate(Double.class, "RAND()").asc())
                       .limit(3)
                       .fetch();
  }

}