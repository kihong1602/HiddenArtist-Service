package com.hiddenartist.backend.domain.account.persistence.repository;


import static com.hiddenartist.backend.domain.account.persistence.QAccount.account;
import static com.hiddenartist.backend.domain.artist.persistence.QArtist.artist;
import static com.hiddenartist.backend.domain.artist.persistence.QFollowArtist.followArtist;
import static com.hiddenartist.backend.domain.mentor.persistence.QMentor.mentor;
import static com.hiddenartist.backend.domain.mentoring.persistence.QMentoring.mentoring;
import static com.hiddenartist.backend.domain.mentoring.persistence.QMentoringApplication.mentoringApplication;

import com.hiddenartist.backend.domain.account.controller.response.AccountGetMentoringApplicationResponse;
import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.mentoring.persistence.MentoringApplication;
import com.hiddenartist.backend.global.exception.type.EntityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.utils.QueryDslUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomAccountRepositoryImpl implements CustomAccountRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public void removeFollowArtists(String email, List<String> tokens) {
    List<Artist> artists = queryFactory.selectFrom(artist)
                                       .where(artist.token.in(tokens))
                                       .fetch();
    if (artists.isEmpty()) {
      return;
    }
    queryFactory.delete(followArtist)
                .where(
                    followArtist.artist.in(artists)
                                       .and(followArtist.account.email.eq(email))
                ).execute();
  }

  @Override
  public List<Artist> findFollowArtistListByEmail(String email) {
    Account findAccount = Optional.ofNullable(
                                      queryFactory.selectFrom(account).where(account.email.eq(email)).fetchOne())
                                  .orElseThrow(() -> new EntityException(ServiceErrorCode.USER_NOT_FOUND));
    return queryFactory.select(artist)
                       .from(followArtist)
                       .join(followArtist.artist, artist)
                       .where(followArtist.account.eq(findAccount))
                       .fetch();
  }

  @Override
  public Page<AccountGetMentoringApplicationResponse> findMentoringApplicationByEmail(Pageable pageable, String email) {
    List<AccountGetMentoringApplicationResponse> result = queryFactory.select(
                                                                          Projections.constructor(AccountGetMentoringApplicationResponse.class,
                                                                              mentoringApplication.token,
                                                                              mentoringApplication.applicationTime,
                                                                              mentoringApplication.mentoringApplicationStatus,
                                                                              mentoring.name,
                                                                              mentoring.amount,
                                                                              mentoring.token,
                                                                              account.nickname
                                                                          )
                                                                      ).from(mentoringApplication)
                                                                      .leftJoin(mentoringApplication.account, account)
                                                                      .leftJoin(mentoringApplication.mentoring, mentoring)
                                                                      .leftJoin(mentoring.mentor, mentor)
                                                                      .leftJoin(mentor.account, account)
                                                                      .where(mentoringApplication.account.email.eq(email))
                                                                      .orderBy(
                                                                          QueryDslUtils.createOrderSpecifier(pageable.getSort(),
                                                                              MentoringApplication.class, mentoringApplication)
                                                                      )
                                                                      .offset(pageable.getOffset())
                                                                      .limit(pageable.getPageSize())
                                                                      .fetch();

    JPAQuery<Long> countQuery = queryFactory.select(mentoringApplication.count())
                                            .from(mentoringApplication)
                                            .leftJoin(mentoringApplication.account, account)
                                            .where(account.email.eq(email));
    return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
  }

}