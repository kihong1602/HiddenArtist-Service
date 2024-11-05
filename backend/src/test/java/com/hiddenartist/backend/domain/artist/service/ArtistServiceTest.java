package com.hiddenartist.backend.domain.artist.service;

import static com.hiddenartist.backend.domain.artist.persistence.type.ContactType.EMAIL;
import static com.hiddenartist.backend.domain.artist.persistence.type.ContactType.SNS;
import static com.hiddenartist.backend.domain.artist.persistence.type.ContactType.TEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.hiddenartist.backend.domain.artist.controller.response.ArtistDetailResponse;
import com.hiddenartist.backend.domain.artist.controller.response.ArtistGetAllArtworkResponse;
import com.hiddenartist.backend.domain.artist.controller.response.ArtistGetListResponse;
import com.hiddenartist.backend.domain.artist.controller.response.ArtistGetSignatureArtworkResponse;
import com.hiddenartist.backend.domain.artist.controller.response.ArtistGetThreeResponse;
import com.hiddenartist.backend.domain.artist.controller.response.ArtistSimpleResponse;
import com.hiddenartist.backend.domain.artist.controller.response.SignatureArtworkResponse;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artist.persistence.ArtistContact;
import com.hiddenartist.backend.domain.artist.persistence.repository.ArtistRepository;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

  @Mock
  private ArtistRepository artistRepository;

  @InjectMocks
  private ArtistService artistService;

  @Test
  @DisplayName("모든 작가 조회")
  void getAllArtists() {
    //given
    int totalPage = 42;
    List<ArtistSimpleResponse> artistSimpleRespons = IntStream.rangeClosed(1, 12)
                                                              .mapToObj(this::createSimpleArtistResponse)
                                                              .toList();
    Page<ArtistSimpleResponse> responses = new PageImpl<>(artistSimpleRespons, PageRequest.of(0, 12), totalPage);
    given(artistRepository.findAllArtists(any(Pageable.class))).willReturn(responses);

    //when
    ArtistGetListResponse artistGetListResponse = artistService.getAllArtists(PageRequest.of(1, 12));

    //then
    assertThat(artistGetListResponse).isNotNull();
    assertThat(artistGetListResponse.artists().getNumber()).isEqualTo(0);
    assertThat(artistGetListResponse.artists().getContent())
        .hasSize(12)
        .extracting("name", "image", "summary", "token")
        .containsExactly(
            tuple("artist1", "artist_image1", "summary1", "1"),
            tuple("artist2", "artist_image2", "summary2", "2"),
            tuple("artist3", "artist_image3", "summary3", "3"),
            tuple("artist4", "artist_image4", "summary4", "4"),
            tuple("artist5", "artist_image5", "summary5", "5"),
            tuple("artist6", "artist_image6", "summary6", "6"),
            tuple("artist7", "artist_image7", "summary7", "7"),
            tuple("artist8", "artist_image8", "summary8", "8"),
            tuple("artist9", "artist_image9", "summary9", "9"),
            tuple("artist10", "artist_image10", "summary10", "10"),
            tuple("artist11", "artist_image11", "summary11", "11"),
            tuple("artist12", "artist_image12", "summary12", "12")
        );

  }

  @Test
  @DisplayName("Token을 통해 작가 상세정보 조회")
  void getArtistDetailTest() {
    //given
    String tokenValue = "1";
    Artist artist = Artist.builder()
                          .name("artist1")
                          .profileImage("artist_image1")
                          .birth(LocalDate.of(1997, 10, 24))
                          .summary("summary1")
                          .token(EntityToken.ARTIST.identifyToken(tokenValue))
                          .description("description1")
                          .build();
    ArtistContact artistContact1 = ArtistContact.builder()
                                                .type(SNS)
                                                .label("instagram")
                                                .contactValue("instagram_url")
                                                .build();
    ArtistContact artistContact2 = ArtistContact.builder()
                                                .type(EMAIL)
                                                .label("email")
                                                .contactValue("test.artist@test.com")
                                                .build();
    List<ArtistContact> artistContacts = List.of(artistContact1, artistContact2);
    List<Genre> genres = List.of(new Genre("현대미술"), new Genre("인물화"));
    ArtistDetailResponse expectedResponse = ArtistDetailResponse.create(artist, artistContacts, genres);

    given(artistRepository.findArtistDetailByToken(EntityToken.ARTIST.identifyToken(tokenValue)))
        .willReturn(expectedResponse);

    //when
    ArtistDetailResponse artistDetail = artistService.getArtistDetail(tokenValue);
    //then
    assertThat(artistDetail).isNotNull().isEqualTo(expectedResponse);
    assertThat(artistDetail.getGenres()).hasSize(2).containsExactlyInAnyOrder("현대미술", "인물화");
    assertThat(artistDetail.getContacts()).hasSize(3);
    assertThat(artistDetail.getContacts().get(TEL)).isEmpty();
    assertThat(artistDetail.getContacts().get(SNS)).extracting("label", "value")
                                                   .containsExactly(tuple("instagram", "instagram_url"));
    assertThat(artistDetail.getContacts().get(EMAIL)).extracting("label", "value")
                                                     .containsExactly(tuple("email", "test.artist@test.com"));
  }

  @Test
  @DisplayName("가장 Follower가 많은 작가 3명 조회")
  void getPopularArtistsTest() {
    //given
    List<Artist> artists = IntStream.rangeClosed(1, 3)
                                    .mapToObj(this::createArtist)
                                    .toList();
    List<ArtistSimpleResponse> artistSimpleRespons = artists.stream().map(ArtistSimpleResponse::convert).toList();
    ArtistGetThreeResponse expectedResponse = new ArtistGetThreeResponse(artistSimpleRespons);
    given(artistRepository.findPopularArtists()).willReturn(artists);

    //when
    ArtistGetThreeResponse popularArtists = artistService.getPopularArtists();
    //then
    assertThat(popularArtists).isNotNull().isEqualTo(expectedResponse);
    assertThat(popularArtists.artists()).hasSize(3)
                                        .extracting("name", "summary", "token")
                                        .containsExactly(
                                            tuple(artists.get(0).getName(), artists.get(0).getSummary(), "1"),
                                            tuple(artists.get(1).getName(), artists.get(1).getSummary(), "2"),
                                            tuple(artists.get(2).getName(), artists.get(2).getSummary(), "3")
                                        );
  }

  @Test
  @DisplayName("새로 등록된 작가 3명 조회")
  void getNewArtistsTest() {
    //given
    List<Artist> artists = IntStream.rangeClosed(1, 3).mapToObj(this::createArtist).toList();
    given(artistRepository.findNewArtists()).willReturn(artists);

    //when
    ArtistGetThreeResponse newArtists = artistService.getNewArtists();

    //then
    assertThat(newArtists).isNotNull();
    assertThat(newArtists.artists()).hasSize(3)
                                    .extracting("name", "summary", "token")
                                    .containsExactly(
                                        tuple(artists.get(0).getName(), artists.get(0).getSummary(), "1"),
                                        tuple(artists.get(1).getName(), artists.get(1).getSummary(), "2"),
                                        tuple(artists.get(2).getName(), artists.get(2).getSummary(), "3")
                                    );
  }

  @Test
  @DisplayName("Token을 통해 작가 대표작품 조회")
  void getArtistSignatureArtworksTest() {
    //given
    String tokenValue = "1";
    List<SignatureArtworkResponse> artworkResponses = IntStream.rangeClosed(1, 3)
                                                               .mapToObj(
                                                                   count -> new SignatureArtworkResponse(
                                                                       "artwork" + count,
                                                                       EntityToken.ARTWORK.identifyToken(
                                                                           String.valueOf(count)),
                                                                       "artwork_image" + count,
                                                                       (byte) count))
                                                               .toList();
    given(artistRepository.findSignatureArtworkByToken(EntityToken.ARTIST.identifyToken(tokenValue)))
        .willReturn(artworkResponses);

    //when
    ArtistGetSignatureArtworkResponse artistSignatureArtworks = artistService.getArtistSignatureArtworks(tokenValue);

    //then
    assertThat(artistSignatureArtworks).isNotNull();
    assertThat(artistSignatureArtworks.artworks()).hasSize(3)
                                                  .extracting("name", "image", "token", "displayOrder")
                                                  .containsExactly(
                                                      tuple("artwork1", "artwork_image1", "1", (byte) 1),
                                                      tuple("artwork2", "artwork_image2", "2", (byte) 2),
                                                      tuple("artwork3", "artwork_image3", "3", (byte) 3)
                                                  );
  }

  @Test
  @DisplayName("Token을 통해 작가의 모든 작품 조회")
  void getArtistAllArtworksTest() {
    //given
    String tokenValue = "1";
    List<Artwork> artworks = IntStream.rangeClosed(1, 20).mapToObj(this::createArtwork).toList();
    given(artistRepository.findAllArtworkByToken(EntityToken.ARTIST.identifyToken(tokenValue))).willReturn(artworks);

    //when
    ArtistGetAllArtworkResponse artistAllArtworks = artistService.getArtistAllArtworks(tokenValue);
    //then
    assertThat(artistAllArtworks).isNotNull().hasFieldOrProperty("artworks");
    assertThat(artistAllArtworks.artworks()).isNotEmpty().hasSize(20);
  }

  private ArtistSimpleResponse createSimpleArtistResponse(int count) {
    return new ArtistSimpleResponse(
        "artist" + count,
        EntityToken.ARTIST.identifyToken(String.valueOf(count)),
        "artist_image" + count,
        "summary" + count);
  }

  private Artist createArtist(int count) {
    return Artist.builder()
                 .name("artist" + count)
                 .profileImage("artist_image" + count)
                 .summary("summary" + count)
                 .token(EntityToken.ARTIST.identifyToken(
                     String.valueOf(count)))
                 .build();
  }

  private Artwork createArtwork(int count) {
    return Artwork.builder()
                  .name("artwork" + count)
                  .image("artwork_image" + count)
                  .token(EntityToken.ARTWORK.identifyToken(String.valueOf(count)))
                  .productionYear(LocalDate.of(2021, 1, 1).plusYears(count))
                  .build();
  }

}