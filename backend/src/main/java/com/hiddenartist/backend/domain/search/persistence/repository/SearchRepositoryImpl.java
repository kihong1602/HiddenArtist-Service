package com.hiddenartist.backend.domain.search.persistence.repository;

import static com.hiddenartist.backend.domain.account.persistence.QAccount.account;
import static com.hiddenartist.backend.domain.artist.persistence.QArtist.artist;
import static com.hiddenartist.backend.domain.artwork.persistence.QArtwork.artwork;
import static com.hiddenartist.backend.domain.exhibition.persistence.QExhibition.exhibition;
import static com.hiddenartist.backend.domain.genre.persistence.QGenre.genre;
import static com.hiddenartist.backend.domain.mentor.persistence.QMentor.mentor;
import static com.hiddenartist.backend.domain.mentoring.persistence.QMentoring.mentoring;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository {

  private static final String FULL_TEXT_SEARCH_SQL = "function('match',{0},{1})";
  private final JPAQueryFactory queryFactory;

  @Override
  public List<Artist> findArtistByKeyword(String keyword) {
    return findByKeyword(artist, artist.name, keyword);
  }

  @Override
  public List<Artwork> findArtworkByKeyword(String keyword) {
    return findByKeyword(artwork, artwork.name, keyword);
  }

  @Override
  public List<Genre> findGenreByKeyword(String keyword) {
    return queryFactory.selectFrom(genre).where(fullTextSearch(genre.name, keyword)).fetch();
  }

  @Override
  public List<Exhibition> findExhibitionByKeyword(String keyword) {
    return findByKeyword(exhibition, exhibition.name, keyword);
  }

  @Override
  public List<Mentoring> findMentoringByKeyword(String keyword) {
    return queryFactory.selectFrom(mentoring)
                       .leftJoin(mentoring.mentor, mentor)
                       .fetchJoin()
                       .leftJoin(mentor.account, account)
                       .fetchJoin()
                       .where(fullTextSearch(mentoring.name, keyword))
                       .fetch();
  }

  private <T> List<T> findByKeyword(EntityPath<T> entity, StringExpression column, String keyword) {
    return queryFactory.selectFrom(entity)
                       .where(fullTextSearch(column, keyword))
                       .limit(10)
                       .fetch();
  }

  private BooleanExpression fullTextSearch(StringExpression idx, String keyword) {
    return StringUtils.hasText(keyword) ?
        Expressions.numberTemplate(Double.class, FULL_TEXT_SEARCH_SQL, idx, keyword).gt(0) : null;
  }

}