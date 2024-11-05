package com.hiddenartist.backend.domain.artist.persistence;

import static com.hiddenartist.backend.domain.artist.persistence.type.ContactType.EMAIL;
import static com.hiddenartist.backend.domain.artist.persistence.type.ContactType.SNS;
import static com.hiddenartist.backend.domain.artist.persistence.type.ContactType.TEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.hiddenartist.backend.domain.artist.controller.response.ArtistDetailResponse;
import com.hiddenartist.backend.domain.artist.controller.response.ArtistSimpleResponse;
import com.hiddenartist.backend.domain.artist.persistence.repository.ArtistRepository;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.global.config.AbstractMySQLRepositoryTest;
import com.hiddenartist.backend.global.config.TestDataInitializer;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class ArtistRepositoryTest extends AbstractMySQLRepositoryTest {

  @Autowired
  private TestDataInitializer initializer;

  @Autowired
  private ArtistRepository artistRepository;

  @Test
  @DisplayName("작가 전체 조회 테스트")
  void getAllArtistsTest() {
    //given
    initializer.saveArtists(120);
    Pageable pageRequest = PageRequest.of(1, 12, Sort.by(Direction.ASC, "name"));
    //when
    Page<ArtistSimpleResponse> artists = artistRepository.findAllArtists(pageRequest);
    //then
    assertThat(artists).hasSize(12);
    assertThat(artists.getTotalPages()).isEqualTo(10);
    assertThat(artists.getPageable().getPageNumber()).isEqualTo(1);
  }

  @Test
  @DisplayName("작가 상세정보 조회 테스트")
  void getArtistDetailTest() {
    //given
    initializer.saveArtists(1);
    ArtistDetailResponse expectedResponse = createExpectedResponse();
    //when
    ArtistDetailResponse response = artistRepository.findArtistDetailByToken(EntityToken.ARTIST.identifyToken("1"));

    //then
    assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
  }

  private ArtistDetailResponse createExpectedResponse() {
    Artist artist = Artist.builder()
                          .name("artist1")
                          .profileImage("test profile image 1")
                          .birth(LocalDate.of(1991, 2, 2))
                          .summary("test summary 1")
                          .description("test description 1")
                          .token(EntityToken.ARTIST.identifyToken("1"))
                          .build();
    List<Genre> genres = Stream.of("현대미술", "조소", "추상화").map(Genre::new).toList();
    ArtistContact contact1 = ArtistContact.builder().type(SNS).label("instagram").contactValue("instagramUrl").build();
    ArtistContact contact2 = ArtistContact.builder().type(SNS).label("x(twitter)").contactValue("twitterUrl").build();
    ArtistContact contact3 = ArtistContact.builder().type(SNS).label("github").contactValue("githubUrl").build();
    ArtistContact contact4 = ArtistContact.builder().type(EMAIL).label("email").contactValue("test@test.com").build();
    ArtistContact contact5 = ArtistContact.builder().type(TEL).label("tel").contactValue("010-1234-5678").build();
    List<ArtistContact> contacts = List.of(contact1, contact2, contact3, contact4, contact5);
    return ArtistDetailResponse.create(artist, contacts, genres);
  }


  @Test
  @DisplayName("Popular Artist 조회 테스트")
  void getPopularArtistsTest() {
    //given
    initializer.saveAccounts(5);
    initializer.saveArtists(10);
    initializer.saveFollowArtists(5);
    //when
    List<Artist> popularArtists = artistRepository.findPopularArtists();
    //then
    assertThat(popularArtists)
        .hasSize(3)
        .extracting("name")
        .containsExactly(
            "artist1", "artist2", "artist3"
        );
  }

  @Test
  @DisplayName("New Artist 조회 테스트")
  void getNewArtistsTest() {
    //given
    initializer.saveArtists(5);
    //when
    List<Artist> newArtists = artistRepository.findNewArtists();
    //then
    assertThat(newArtists).hasSize(3)
                          .extracting("name", "token")
                          .containsExactly(
                              tuple("artist5", EntityToken.ARTIST.identifyToken("5")),
                              tuple("artist4", EntityToken.ARTIST.identifyToken("4")),
                              tuple("artist3", EntityToken.ARTIST.identifyToken("3"))
                          );

  }

}