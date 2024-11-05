package com.hiddenartist.backend.domain.mentor.persistence.repository;

import static com.hiddenartist.backend.domain.account.persistence.QAccount.account;
import static com.hiddenartist.backend.domain.mentor.persistence.QMentor.mentor;
import static com.hiddenartist.backend.domain.mentoring.persistence.QMentoring.mentoring;
import static com.hiddenartist.backend.domain.mentoring.persistence.QMentoringApplication.mentoringApplication;

import com.hiddenartist.backend.domain.mentor.controller.response.ReceivedMentoringApplicationResponse;
import com.hiddenartist.backend.domain.mentoring.persistence.MentoringApplication;
import com.hiddenartist.backend.global.utils.QueryDslUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomMentorRepositoryImpl implements CustomMentorRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<ReceivedMentoringApplicationResponse> getReceivedMentoringApplications(Pageable pageable, String email) {
    List<ReceivedMentoringApplicationResponse> result = queryFactory.select(
                                                                        Projections.constructor(ReceivedMentoringApplicationResponse.class,
                                                                            mentoringApplication.token,
                                                                            mentoringApplication.applicationTime,
                                                                            mentoringApplication.mentoringApplicationStatus,
                                                                            mentoring.name,
                                                                            account.nickname)
                                                                    ).from(mentoringApplication)
                                                                    .leftJoin(mentoringApplication.mentoring, mentoring)
                                                                    .leftJoin(mentoringApplication.account, account)
                                                                    .leftJoin(mentoring.mentor, mentor)
                                                                    .leftJoin(mentor.account)
                                                                    .where(mentor.account.email.eq(email))
                                                                    .orderBy(
                                                                        QueryDslUtils.createOrderSpecifier(pageable.getSort(),
                                                                            MentoringApplication.class, mentoringApplication))
                                                                    .offset(pageable.getOffset())
                                                                    .limit(pageable.getPageSize())
                                                                    .fetch();

    JPAQuery<Long> countQuery = queryFactory.select(mentoringApplication.count()).from(mentoringApplication)
                                            .leftJoin(mentoringApplication.mentoring, mentoring)
                                            .leftJoin(mentoring.mentor, mentor)
                                            .leftJoin(mentor.account, account)
                                            .where(account.email.eq(email));
    return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
  }

}