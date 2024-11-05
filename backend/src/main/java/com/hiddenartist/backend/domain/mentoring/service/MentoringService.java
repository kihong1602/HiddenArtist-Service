package com.hiddenartist.backend.domain.mentoring.service;

import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringDetailResponse;
import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringSimpleResponse;
import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringUnavailableTime;
import com.hiddenartist.backend.domain.mentoring.persistence.LockApplicationTime;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.mentoring.persistence.MentoringApplication;
import com.hiddenartist.backend.domain.mentoring.persistence.repository.MentoringRepository;
import com.hiddenartist.backend.global.aop.DistributedLock;
import com.hiddenartist.backend.global.redis.LockApplicationTimeClient;
import com.hiddenartist.backend.global.redis.RedisLockClient;
import com.hiddenartist.backend.global.type.EntityToken;
import com.hiddenartist.backend.global.utils.PageUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MentoringService {

  private final MentoringRepository mentoringRepository;
  private final LockApplicationTimeClient redisClient;
  private final RedisLockClient redisLockClient;

  @Transactional(readOnly = true)
  public Page<MentoringSimpleResponse> getAllMentorings(Pageable pageable) {
    Pageable pageRequest = PageUtils.createPageRequest(pageable);
    Page<Mentoring> mentorings = mentoringRepository.findAllMentorings(pageRequest);
    return mentorings.map(MentoringSimpleResponse::create);
  }

  @Transactional(readOnly = true)
  public MentoringDetailResponse getMentoringDetails(String tokenValue) {
    String token = EntityToken.MENTORING.identifyToken(tokenValue);
    return mentoringRepository.findMentoringByToken(token);
  }

  @Transactional
  @DistributedLock(key = "#token + ':' + #applicationTime.format(T(java.time.format.DateTimeFormatter).ofPattern('yyyyMMddHHmm'))", waitTime = 10, leaseTime = 2)
  public void reservationApplicationTime(String token, LocalDateTime applicationTime, String email) {
    LockApplicationTime lockApplicationTime = new LockApplicationTime(email, token, applicationTime);
    redisLockClient.reservationApplicationTime(lockApplicationTime);
  }

  @Transactional
  public MentoringUnavailableTime getMentoringUnavailableTimes(String tokenValue, LocalDate selectMonth) {
    // redis에서 해당 월에 신청중인 데이터 조회
    List<LockApplicationTime> list = redisClient.findByKeyword(tokenValue, selectMonth);
    List<LocalDateTime> reservationTimes = list.stream().map(LockApplicationTime::getApplicationTime).toList();

    // DB에서 해당 월에 신청된 데이터 조회
    String token = EntityToken.MENTORING.identifyToken(tokenValue);
    List<MentoringApplication> mentoringApplications = mentoringRepository.findMentoringApplicationByMonth(token, selectMonth);
    List<LocalDateTime> applicationTimes = mentoringApplications.stream().map(MentoringApplication::getApplicationTime).toList();

    return MentoringUnavailableTime.create(reservationTimes, applicationTimes);
  }

  @Transactional
  public void unlockReservationApplicationTime(String tokenValue, LocalDateTime applicationTime) {
    String key = LockApplicationTimeClient.generateKey(tokenValue, applicationTime);
    redisClient.deleteBy(key);
  }

}