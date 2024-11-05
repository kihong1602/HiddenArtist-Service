package com.hiddenartist.backend.domain.exhibition.persistence.repository;

import static com.hiddenartist.backend.domain.exhibition.persistence.QExhibition.exhibition;
import static com.hiddenartist.backend.domain.exhibition.persistence.QExhibitionLocation.exhibitionLocation;
import static com.hiddenartist.backend.domain.exhibition.persistence.QExhibitionManager.exhibitionManager;

import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionSimpleResponse;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.global.utils.QueryDslUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomExhibitionRepositoryImpl implements CustomExhibitionRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<ExhibitionSimpleResponse> findAllExhibitions(Pageable pageable) {
    List<ExhibitionSimpleResponse> result = queryFactory.select(
                                                            Projections.constructor(ExhibitionSimpleResponse.class,
                                                                exhibition.name,
                                                                exhibition.token,
                                                                exhibition.image,
                                                                exhibition.startDate,
                                                                exhibition.endDate))
                                                        .from(exhibition)
                                                        .where(exhibition.endDate.goe(LocalDate.now()))
                                                        .offset(pageable.getOffset())
                                                        .limit(pageable.getPageSize())
                                                        .orderBy(
                                                            QueryDslUtils.createOrderSpecifier(pageable.getSort(),
                                                                Exhibition.class, exhibition)
                                                        )
                                                        .fetch();
    JPAQuery<Long> countQuery = queryFactory.select(exhibition.count()).from(exhibition);
    return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
  }

  @Override
  public Optional<Exhibition> findExhibitionByToken(String token) {
    return Optional.ofNullable(queryFactory.selectFrom(exhibition)
                                           .leftJoin(exhibition.location, exhibitionLocation)
                                           .fetchJoin()
                                           .leftJoin(exhibition.manager, exhibitionManager)
                                           .fetchJoin()
                                           .where(exhibition.token.eq(token))
                                           .fetchOne());
  }

  @Override
  public List<Exhibition> findCurrentExhibitions(LocalDate now) {
    return queryFactory.selectFrom(exhibition)
                       .where(exhibition.startDate.loe(now)
                                                  .and(exhibition.endDate.goe(now)))
                       .orderBy(exhibition.endDate.asc(), exhibition.name.asc())
                       .limit(10)
                       .fetch();
  }

  @Override
  public List<Exhibition> findUpcomingExhibitions(LocalDate now) {
    return queryFactory.selectFrom(exhibition)
                       .where(exhibition.startDate.after(now))
                       .orderBy(exhibition.startDate.asc(), exhibition.name.asc())
                       .limit(10)
                       .fetch();
  }

  @Override
  public Page<ExhibitionSimpleResponse> findPastExhibitions(Pageable pageable, LocalDate now) {
    List<ExhibitionSimpleResponse> result = queryFactory.select(Projections.constructor(ExhibitionSimpleResponse.class,
                                                            exhibition.name,
                                                            exhibition.token,
                                                            exhibition.image,
                                                            exhibition.startDate,
                                                            exhibition.endDate))
                                                        .from(exhibition)
                                                        .where(exhibition.endDate.before(now))
                                                        .offset(pageable.getOffset())
                                                        .limit(pageable.getPageSize())
                                                        .orderBy(QueryDslUtils.createOrderSpecifier(pageable.getSort(),
                                                            Exhibition.class, exhibition))
                                                        .fetch();
    JPAQuery<Long> countQuery = queryFactory.select(exhibition.count()).from(exhibition);
    return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
  }

}