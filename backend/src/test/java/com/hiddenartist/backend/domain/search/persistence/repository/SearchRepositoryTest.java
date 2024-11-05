package com.hiddenartist.backend.domain.search.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import com.hiddenartist.backend.domain.account.persistence.type.Role;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.domain.mentor.persistence.type.Career;
import com.hiddenartist.backend.domain.mentor.persistence.type.CertificationStatus;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringStatus;
import com.hiddenartist.backend.global.config.AbstractMySQLRepositoryTest;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * Full Text Index는 DB에 Commit이 되어야 생성됩니다.
 * @Transactional이 적용되면 DB에 Commit되지 않고 테스트 종료 시 Rollback되기 때문에
 * 검색 테스트에서 트랜잭션을 비활성화하고, TearDown 메서드를 통해 수동으로 테스트 데이터를 정리합니다.
 * 이렇게 하면 데이터베이스 상태를 유지하면서 Full Text Index의 동작을 검증할 수 있으며,
 * 각 테스트가 독립적으로 실행될 수 있도록 데이터를 일괄 삭제합니다.
 */
class SearchRepositoryTest extends AbstractMySQLRepositoryTest {

  @Autowired
  private SearchRepository searchRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @AfterEach
  void tearDown() {
    jdbcTemplate.execute("delete from artist");
    jdbcTemplate.execute("delete from artwork");
    jdbcTemplate.execute("delete from genre");
    jdbcTemplate.execute("delete from exhibition");
    jdbcTemplate.execute("delete from mentoring");
    jdbcTemplate.execute("delete from mentor");
    jdbcTemplate.execute("delete from account");
  }

  // 아티스트 조회
  @Test
  @DisplayName("검색어 입력시 작가 최대 10명 조회")
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  void searchArtistsByKeywordTest() {
    //given
    saveArtists();
    String keyword = "이현";

    //when
    List<Artist> artists = searchRepository.findArtistByKeyword(keyword);

    //then
    assertThat(artists).hasSize(10)
                       .filteredOn(artist -> artist.getName().contains(keyword))
                       .isNotEmpty();
  }

  // 작품 조회
  @Test
  @DisplayName("검색어 입력시 작품 최대 10점 조회")
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  void searchArtworksByKeywordTest() {
    //given
    saveArtworks();
    String keyword = "별빛";

    //when
    List<Artwork> artworks = searchRepository.findArtworkByKeyword(keyword);

    //then
    assertThat(artworks).hasSize(10)
                        .filteredOn(artwork -> artwork.getName().contains(keyword))
                        .isNotEmpty();
  }

  // 장르 조회
  @Test
  @DisplayName("검색어에 부합하는 장르 조회")
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  void searchGenresByKeywordTest() {
    //given
    saveGenres();
    String keyword = "추상";

    //when
    List<Genre> genres = searchRepository.findGenreByKeyword(keyword);

    //then
    assertThat(genres).hasSize(12)
                      .filteredOn(genre -> genre.getName().contains(keyword))
                      .isNotEmpty();
  }

  // 전시회 조회
  @Test
  @DisplayName("검색어 입력시 전시회 최대 10개 조회")
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  void searchExhibitionsByKeywordTest() {
    //given
    saveExhibitions();
    String keyword = "예술";

    //when
    List<Exhibition> exhibitions = searchRepository.findExhibitionByKeyword(keyword);

    //then
    assertThat(exhibitions).hasSize(10)
                           .filteredOn(exhibition -> exhibition.getName().contains(keyword))
                           .isNotEmpty();
  }

  // 멘토링 조회
  @Test
  @DisplayName("검색어에 부합하는 멘토링 조회")
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  void searchMentoringsByKeywordTest() {
    //given
    saveMentorings();
    String keyword = "창의";

    //when
    List<Mentoring> mentorings = searchRepository.findMentoringByKeyword(keyword);

    //then
    assertThat(mentorings).hasSize(10)
                          .filteredOn(mentoring -> mentoring.getName().contains(keyword))
                          .isNotEmpty();
  }

  private void saveArtists() {
    List<String> names = List.of(
        "이현우",
        "김이현",
        "이현수",
        "박이현",
        "이현석",
        "정이현",
        "이현진",
        "최이현",
        "이현지",
        "한이현",
        "이현경",
        "류현진"
    );

    jdbcTemplate.batchUpdate("insert into artist (name) values (?)", names, names.size(), (ps, name) -> ps.setString(1, name));

  }

  private void saveArtworks() {
    List<String> names = List.of(
        "꿈의 별빛",
        "바다 위 별빛",
        "달과 별빛",
        "시간 속의 별빛",
        "별빛의 여정",
        "별빛 속에서",
        "어둠을 비추는 별빛",
        "별빛 아래서",
        "숲을 감싸는 별빛",
        "피 땀 눈물",
        "저녁 하늘 별빛",
        "햇살이 나뭇잎을 핥고 있었다."
    );
    jdbcTemplate.batchUpdate("insert into artwork (name) values (?)", names, names.size(), (ps, name) -> ps.setString(1, name));
  }

  private void saveGenres() {
    List<String> names = List.of("추상 미술", "초추상적", "추상 회화", "추상 팝아트", "입체 추상", "추상적 감각", "추상 판화", "추상 조각", "추상 미디어 아트", "추상 공예",
        "서양 추상", "추상화", "현대 미술", "중세 인물화");
    jdbcTemplate.batchUpdate("insert into genre (name) values (?)", names, names.size(), (ps, name) -> ps.setString(1, name));
  }

  private void saveExhibitions() {
    List<String> names = List.of(
        "예술의 경계",
        "현대 예술 탐구",
        "빛과 예술의 만남",
        "예술 속의 자연",
        "미래 예술전",
        "예술과 추상",
        "색채 예술 연대기",
        "예술적 시선",
        "예술과 공감",
        "예술적으로 느껴졌어",
        "Fantastic Artists Exhibition",
        "용이 내가 된다!"
    );
    jdbcTemplate.batchUpdate("insert into exhibition (name) values (?)", names, names.size(),
        (ps, name) -> ps.setString(1, name));
  }

  private void saveMentorings() {
    List<String> accountNickNames = List.of(
        "김이현",
        "이현수",
        "박이현",
        "이현석",
        "정이현",
        "이현진",
        "최이현",
        "이현지",
        "한이현",
        "이현경",
        "류현진",
        "김지수"
    );
    jdbcTemplate.batchUpdate("insert into account (id,nickname,role,provider_type) values (?,?,?,?)", accountNickNames,
        accountNickNames.size(),
        (ps, name) -> {
          ps.setLong(1, accountNickNames.indexOf(name) + 1);
          ps.setString(2, name);
          ps.setString(3, Role.USER.name());
          ps.setString(4, ProviderType.KAKAO.name());
        });

    List<Long> ids = LongStream.rangeClosed(1, 12).boxed().toList();
    jdbcTemplate.batchUpdate("insert into mentor (id,account_id,career,certification_status) values (?,?,?,?)", ids, ids.size(),
        (ps, id) -> {
          ps.setLong(1, id);
          ps.setLong(2, id);
          ps.setString(3, Career.MIDDLE.name());
          ps.setString(4, CertificationStatus.VERIFIED.name());
        });

    List<String> names = List.of(
        "창의성을 키우는 미술 실습",
        "색채와 창의성 탐구",
        "예술과 창의성의 만남",
        "창의성 향상을 위한 그림 기법",
        "예술가의 창의성 개발",
        "창의성과 표현의 자유",
        "현대 미술 속 창의성 발견",
        "창의적 사고를 위한 예술 멘토링",
        "미술 작품 분석과 창의성",
        "창의성으로 확장하는 예술 세계",
        "효율적인 문제 해결과 성장",
        "면접 컨설팅"
    );
    jdbcTemplate.batchUpdate("insert into mentoring (name,mentoring_status,mentor_id) values (?,?,?)", names, names.size(),
        (ps, name) -> {
          ps.setString(1, name);
          ps.setString(2, MentoringStatus.OPEN.name());
          ps.setLong(3, names.indexOf(name) + 1);
        });
  }

}