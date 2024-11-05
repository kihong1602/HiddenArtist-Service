package com.hiddenartist.backend.domain.mentoring.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.type.Role;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.domain.mentor.persistence.Mentor;
import com.hiddenartist.backend.domain.mentor.persistence.type.Career;
import com.hiddenartist.backend.domain.mentor.persistence.type.CertificationStatus;
import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringDetailResponse;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.mentoring.persistence.MentoringApplication;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringApplicationStatus;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringStatus;
import com.hiddenartist.backend.global.config.AbstractMySQLRepositoryTest;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;

class MentoringRepositoryTest extends AbstractMySQLRepositoryTest {

  @Autowired
  private MentoringRepository mentoringRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  @DisplayName("멘토링 전체조회 테스트")
  void getAllMentoringsTest() {
    //given
    initGetAllMentoringData();
    Pageable pageRequest = PageRequest.of(0, 16, Sort.by(Direction.DESC, "totalApplicationCount"));

    //when
    Page<Mentoring> mentorings = mentoringRepository.findAllMentorings(pageRequest);

    //then
    assertThat(mentorings.getContent()).hasSize(16);
  }

  @Test
  @DisplayName("멘토링 상세정보 조회 테스트: 리뷰 X")
  void getMentoringDetailTest() {
    //given
    initGetMentoringDetailDataWithoutReview();
    String token = EntityToken.MENTORING.identifyToken("1");

    //when
    MentoringDetailResponse result = mentoringRepository.findMentoringByToken(token);

    //then
    assertThat(result).isNotNull()
                      .extracting("name", "image", "content", "amount", "durationTime")
                      .containsExactly("test mentoring name", "test mentoring image", "test mentoring content", 10000, "1시간");
    assertThat(result.getMentor()).isNotNull()
                                  .extracting("name", "profileImage", "career", "organization")
                                  .containsExactly("test nickname", "test profile image", Career.MIDDLE.getDescription(),
                                      "test organization");
    assertThat(result.getReviews()).isEmpty();
  }

  @Test
  @DisplayName("해당 월에 등록된 MentoringApplication 조회")
  void findMentoringApplicationByMonthTest() {
    //given
    initMentoringApplications();
    LocalDate selectMonth = LocalDate.of(2024, 10, 20);
    String token = EntityToken.MENTORING.identifyToken("1");

    //when
    List<MentoringApplication> result = mentoringRepository.findMentoringApplicationByMonth(token, selectMonth);

    //then
    assertThat(result).hasSize(3)
                      .allMatch(application ->
                          application.getApplicationTime().getYear() == 2024 &&
                              application.getApplicationTime().getMonthValue() == 10);
  }

  private void initGetAllMentoringData() {
    List<Account> mentorAccounts = LongStream.rangeClosed(1, 20)
                                             .mapToObj(id ->
                                                 Account.builder()
                                                        .email("mentor" + id + "@test.com")
                                                        .profileImage("test image" + id)
                                                        .nickname("mentor" + id)
                                                        .role(Role.MENTOR)
                                                        .build())
                                             .toList();
    String sql = "insert into account (id,email,profile_image,nickname,role) values(?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, mentorAccounts, mentorAccounts.size(), (ps, mentorAccount) -> {
      ps.setLong(1, mentorAccounts.indexOf(mentorAccount) + 1);
      ps.setString(2, mentorAccount.getEmail());
      ps.setString(3, mentorAccount.getProfileImage());
      ps.setString(4, mentorAccount.getNickname());
      ps.setString(5, mentorAccount.getRole().name());
    });

    List<Mentor> mentors = LongStream.rangeClosed(1, 20)
                                     .mapToObj(id -> Mentor.builder()
                                                           .career(Career.MIDDLE)
                                                           .contactEmail("mentor" + id + "@test.com")
                                                           .organization("test organization" + id)
                                                           .build())
                                     .toList();

    sql = "insert into mentor (id,career,contact_email,organization,certification_status,account_id) values(?,?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, mentors, mentors.size(), (ps, mentor) -> {
      ps.setLong(1, mentors.indexOf(mentor) + 1);
      ps.setString(2, mentor.getCareer().name());
      ps.setString(3, mentor.getContactEmail());
      ps.setString(4, mentor.getOrganization());
      ps.setString(5, CertificationStatus.VERIFIED.name());
      ps.setLong(6, mentors.indexOf(mentor) + 1);
    });

    List<Mentoring> mentorings = LongStream.rangeClosed(1, 20)
                                           .mapToObj(
                                               id -> Mentoring.builder()
                                                              .token("test_" + id)
                                                              .name("test mentoring" + id)
                                                              .image("test mentoring image" + id)
                                                              .mentoringStatus(MentoringStatus.OPEN)
                                                              .amount(10000)
                                                              .durationTime("1시간")
                                                              .totalApplicationCount(0L)
                                                              .build())
                                           .toList();
    sql = "insert into mentoring (id,token,name,image,mentoring_status,amount,duration_time,total_application_count,mentor_id) values(?,?,?,?,?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, mentorings, mentorings.size(), (ps, mentoring) -> {
      ps.setLong(1, mentorings.indexOf(mentoring) + 1);
      ps.setString(2, mentoring.getToken());
      ps.setString(3, mentoring.getName());
      ps.setString(4, mentoring.getImage());
      ps.setString(5, mentoring.getMentoringStatus().name());
      ps.setInt(6, mentoring.getAmount());
      ps.setString(7, mentoring.getDurationTime());
      ps.setLong(8, mentoring.getTotalApplicationCount());
      ps.setLong(9, mentorings.indexOf(mentoring) + 1);
    });

  }

  private void initGetMentoringDetailDataWithoutReview() {
    Account account = Account.builder()
                             .nickname("test nickname")
                             .role(Role.MENTOR)
                             .email("test email")
                             .profileImage("test profile image")
                             .build();
    String sql = "insert into account (id,nickname,email,role,profile_image) value (?,?,?,?,?)";
    jdbcTemplate.update(sql, ps -> {
      ps.setLong(1, 1L);
      ps.setString(2, account.getNickname());
      ps.setString(3, account.getEmail());
      ps.setString(4, account.getRole().name());
      ps.setString(5, account.getProfileImage());
    });

    Mentor mentor = Mentor.builder()
                          .career(Career.MIDDLE)
                          .contactEmail("testmentor@test.com")
                          .organization("test organization")
                          .certificationStatus(CertificationStatus.VERIFIED)
                          .build();
    sql = "insert into mentor (id,career,contact_email,organization,certification_status,account_id) value (?,?,?,?,?,?)";
    jdbcTemplate.update(sql, ps -> {
      ps.setLong(1, 1L);
      ps.setString(2, mentor.getCareer().name());
      ps.setString(3, mentor.getContactEmail());
      ps.setString(4, mentor.getOrganization());
      ps.setString(5, mentor.getCertificationStatus().name());
      ps.setLong(6, 1L);
    });

    Mentoring mentoring = Mentoring.builder()
                                   .name("test mentoring name")
                                   .content("test mentoring content")
                                   .totalApplicationCount(20L)
                                   .amount(10000)
                                   .image("test mentoring image")
                                   .token(EntityToken.MENTORING.identifyToken("1"))
                                   .durationTime("1시간")
                                   .mentoringStatus(MentoringStatus.OPEN)
                                   .build();
    sql = "insert into mentoring (id,name,content,total_application_count,amount,image,token,duration_time,mentoring_status,mentor_id) value(?,?,?,?,?,?,?,?,?,?)";
    jdbcTemplate.update(sql, ps -> {
      ps.setLong(1, 1L);
      ps.setString(2, mentoring.getName());
      ps.setString(3, mentoring.getContent());
      ps.setLong(4, mentoring.getTotalApplicationCount());
      ps.setInt(5, mentoring.getAmount());
      ps.setString(6, mentoring.getImage());
      ps.setString(7, mentoring.getToken());
      ps.setString(8, mentoring.getDurationTime());
      ps.setString(9, mentoring.getMentoringStatus().name());
      ps.setLong(10, 1L);
    });

    List<Genre> genres = List.of(new Genre("현대미술"), new Genre("추상화"), new Genre("팝아트"));
    sql = "insert into genre (id,name) values (?,?)";
    jdbcTemplate.batchUpdate(sql, genres, genres.size(), (ps, genre) -> {
      ps.setLong(1, genres.indexOf(genre) + 1);
      ps.setString(2, genre.getName());
    });

    sql = "insert into mentoring_genre (mentoring_id,genre_id) values (1,?)";
    jdbcTemplate.batchUpdate(sql, genres, genres.size(), (ps, genre) -> ps.setLong(1, genres.indexOf(genre) + 1));
  }


  private void initMentoringApplications() {
    Mentoring mentoring = Mentoring.builder().token(EntityToken.MENTORING.identifyToken("1")).build();
    String sql = "insert into mentoring (id,token) value(1,?)";
    jdbcTemplate.update(sql, ps -> ps.setString(1, mentoring.getToken()));

    List<LocalDateTime> applicationTimes = List.of(
        LocalDateTime.of(2024, 10, 24, 13, 30),
        LocalDateTime.of(2024, 10, 10, 14, 0),
        LocalDateTime.of(2024, 10, 15, 15, 0),
        LocalDateTime.of(2024, 9, 30, 15, 0)
    );

    sql = "insert into mentoring_application (mentoring_id,token,application_time,mentoring_application_status) value(?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, applicationTimes, applicationTimes.size(), (ps, applicationTime) -> {
      String token = EntityToken.MENTORING_APPLICATION.identifyToken(
          String.valueOf(applicationTimes.indexOf(applicationTime) + 1));
      ps.setLong(1, 1);
      ps.setString(2, token);
      ps.setObject(3, applicationTime);
      ps.setString(4, MentoringApplicationStatus.APPROVAL.name());
    });
  }

}