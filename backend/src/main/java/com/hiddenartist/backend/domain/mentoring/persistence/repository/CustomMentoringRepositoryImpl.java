package com.hiddenartist.backend.domain.mentoring.persistence.repository;

import static com.hiddenartist.backend.domain.account.persistence.QAccount.account;
import static com.hiddenartist.backend.domain.genre.persistence.QGenre.genre;
import static com.hiddenartist.backend.domain.genre.persistence.QMentoringGenre.mentoringGenre;
import static com.hiddenartist.backend.domain.mentor.persistence.QMentor.mentor;
import static com.hiddenartist.backend.domain.mentoring.persistence.QMentoring.mentoring;
import static com.hiddenartist.backend.domain.mentoring.persistence.QMentoringApplication.mentoringApplication;
import static com.hiddenartist.backend.domain.mentoring.persistence.QMentoringReview.mentoringReview;

import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringDetailResponse;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.mentoring.persistence.MentoringApplication;
import com.hiddenartist.backend.domain.mentoring.persistence.MentoringReview;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringApplicationStatus;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringStatus;
import com.hiddenartist.backend.global.exception.type.EntityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.utils.QueryDslUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomMentoringRepositoryImpl implements CustomMentoringRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Mentoring> findAllMentorings(Pageable pageable) {
    List<Mentoring> result = queryFactory.selectFrom(mentoring)
                                         .leftJoin(mentoring.mentor, mentor)
                                         .fetchJoin()
                                         .leftJoin(mentor.account, account)
                                         .fetchJoin()
                                         .where(mentoring.mentoringStatus.eq(MentoringStatus.OPEN)
                                                                         .and(mentoring.deleteDate.isNull()))
                                         .offset(pageable.getOffset())
                                         .limit(pageable.getPageSize())
                                         .orderBy(
                                             QueryDslUtils.createOrderSpecifier(pageable.getSort(), Mentoring.class, mentoring))
                                         .fetch();
    JPAQuery<Long> countQuery = queryFactory.select(mentoring.count()).from(mentoring);
    return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
  }

  @Override
  public MentoringDetailResponse findMentoringByToken(String token) {
    Mentoring result = Optional.ofNullable(queryFactory.selectFrom(mentoring)
                                                       .leftJoin(mentoring.mentor, mentor)
                                                       .fetchJoin()
                                                       .leftJoin(mentor.account, account)
                                                       .fetchJoin()
                                                       .where(mentoring.token.eq(token))
                                                       .fetchOne()
    ).orElseThrow(() -> new EntityException(ServiceErrorCode.MENTORING_NOT_FOUND));

    List<Genre> genres = queryFactory.select(genre)
                                     .from(mentoringGenre)
                                     .leftJoin(mentoringGenre.genre, genre)
                                     .where(mentoringGenre.mentoring.eq(result))
                                     .fetch();

    List<MentoringReview> mentoringReviews = queryFactory.selectFrom(mentoringReview)
                                                         .where(mentoringReview.mentoring.token.eq(token))
                                                         .fetch();

    return MentoringDetailResponse.create(result, genres, mentoringReviews);
  }

  @Override
  public List<MentoringApplication> findMentoringApplicationByMonth(String token, LocalDate selectMonth) {
    // DB에 datetime Index 설정 요망
    LocalDateTime startOfMonth = selectMonth.withDayOfMonth(1).atStartOfDay();
    LocalDateTime endOfMonth = selectMonth.plusMonths(1).withDayOfMonth(1).atStartOfDay().minusSeconds(1);
    return queryFactory.selectFrom(mentoringApplication)
                       .leftJoin(mentoringApplication.mentoring, mentoring)
                       .where(
                           mentoringApplication.mentoring.token
                               .eq(token)
                               .and(
                                   mentoringApplication.applicationTime
                                       .between(startOfMonth, endOfMonth)
                               )
                               .and(
                                   mentoringApplication.mentoringApplicationStatus
                                       .ne(MentoringApplicationStatus.CANCELLED)
                               )
                       ).orderBy(mentoringApplication.applicationTime.asc())
                       .fetch();
  }

}