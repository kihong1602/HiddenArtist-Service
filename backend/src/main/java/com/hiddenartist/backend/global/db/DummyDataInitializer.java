package com.hiddenartist.backend.global.db;

import static com.hiddenartist.backend.domain.artist.persistence.type.ContactType.EMAIL;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import com.hiddenartist.backend.domain.account.persistence.type.Role;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artist.persistence.ArtistContact;
import com.hiddenartist.backend.domain.artist.persistence.type.ContactType;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.artwork.persistence.ArtworkMedium;
import com.hiddenartist.backend.global.type.EntityToken;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Profile({"local", "develop"})
@RequiredArgsConstructor
public class DummyDataInitializer {

  private final JdbcTemplate jdbcTemplate;
  private final long ID = 1L;
  private final List<Long> LIST = List.of(1L, 2L, 3L);

  @Transactional
  @EventListener(ApplicationReadyEvent.class)
  protected void init() {
    log.info("DummyData Initializing Start.");
    insertDummyData();
    log.info("DummyData Initializing End.");
  }

  private void insertDummyData() {
    saveAccounts(20);
    saveGenre();
    saveArtists(500);
    saveArtistGenre();
    saveFollowArtists(20);
    saveArtworks(20);
    saveArtworkGenre();
    saveSignatureArtworks();
  }

  private void saveAccounts(int size) {
    List<Account> accounts = IntStream.rangeClosed(1, size).mapToObj(this::createAccount).toList();
    String sql = "insert into account (id,email,nickname,role,provider_type) values (?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, accounts, accounts.size(), (ps, account) -> {
      ps.setLong(1, accounts.indexOf(account) + 1);
      ps.setString(2, account.getEmail());
      ps.setString(3, account.getNickname());
      ps.setString(4, account.getRole().name());
      ps.setString(5, account.getProviderType().name());
    });
  }


  private void saveArtists(int size) {
    saveArtist(size);
    saveArtistContact();
  }

  private void saveArtist(int size) {
    List<Artist> artists = IntStream.rangeClosed(1, size).mapToObj(this::createArtist).toList();
    String sql = "insert into artist (id,name,profile_image,token,summary,description,birth,create_date) values(?,?,?,?,?,?,?,?)";
    LocalDateTime now = LocalDateTime.now();
    jdbcTemplate.batchUpdate(sql, artists, artists.size(), (ps, artist) -> {
      ps.setLong(1, artists.indexOf(artist) + 1);
      ps.setString(2, artist.getName());
      ps.setString(3, artist.getProfileImage());
      ps.setString(4, artist.getToken());
      ps.setString(5, artist.getSummary());
      ps.setString(6, artist.getDescription());
      ps.setDate(7, Date.valueOf(artist.getBirth()));
      ps.setTimestamp(8, Timestamp.valueOf(now.plusDays(artists.indexOf(artist))));
    });
  }

  private void saveArtistContact() {
    List<ArtistContact> artistContacts = createArtistContacts();
    String sql = "insert into artist_contact (type,label,contact_value,artist_id) values(?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, artistContacts, artistContacts.size(), (ps, artistContact) -> {
      ps.setString(1, artistContact.getType().name());
      ps.setString(2, artistContact.getLabel());
      ps.setString(3, artistContact.getContactValue());
      ps.setLong(4, ID);
    });
  }

  private void saveGenre() {
    String sql = "insert into genre (id,name) values (?,?)";
    List<String> genres = List.of("현대미술", "조소", "추상화");
    jdbcTemplate.batchUpdate(sql, genres, genres.size(), (ps, genre) -> {
      ps.setLong(1, genres.indexOf(genre) + 1);
      ps.setString(2, genre);
    });
  }

  private void saveArtistGenre() {
    String sql = "insert into artist_genre (artist_id,genre_id) values(?,?)";
    jdbcTemplate.batchUpdate(sql, LIST, LIST.size(), (ps, id) -> {
      ps.setLong(1, ID);
      ps.setLong(2, id);
    });
  }

  private void saveFollowArtists(int accountSize) {
    List<Long> accountIds = LongStream.rangeClosed(1L, accountSize).boxed().toList();
    String sql = "insert into follow_artist (account_id,artist_id) values (?,?)";
    Random random = new Random();
    jdbcTemplate.batchUpdate(sql, accountIds, accountSize, (ps, accountId) -> {
      Set<Long> artistIds = random.longs(1, 501)
                                  .distinct()
                                  .limit(50)
                                  .boxed().collect(Collectors.toSet());
      for (Long artistId : artistIds) {
        ps.setLong(1, accountId);
        ps.setLong(2, artistId);
        ps.addBatch();
      }
    });
  }

  private void saveArtworks(int size) {
    saveArtworkMediums();
    saveArtwork(size);
  }

  private void saveArtwork(int size) {
    size = Math.max(size, 3);
    List<Artwork> artworks = IntStream.rangeClosed(1, size)
                                      .mapToObj(this::createArtwork)
                                      .toList();
    String sql = "insert into artwork (id,name,image,description,token,width,height,production_year,artwork_medium_id) values (?,?,?,?,?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, artworks, artworks.size(), (ps, artwork) -> {
      int artworkId = artworks.indexOf(artwork) + 1;
      ps.setLong(1, artworkId);
      ps.setString(2, artwork.getName());
      ps.setString(3, artwork.getImage());
      ps.setString(4, artwork.getDescription());
      ps.setString(5, artwork.getToken());
      ps.setDouble(6, artwork.getWidth());
      ps.setDouble(7, artwork.getHeight());
      ps.setDate(8, Date.valueOf(artwork.getProductionYear()));
      ps.setLong(9, ((artworkId) % 5) + 1);
    });

    sql = "insert into artist_artwork (artist_id,artwork_id) values (?,?)";
    jdbcTemplate.batchUpdate(sql, artworks, artworks.size(), (ps, artwork) -> {
      ps.setLong(1, 1L);
      ps.setLong(2, artworks.indexOf(artwork) + 1);
    });
  }

  private void saveArtworkGenre() {
    String sql = "insert into artwork_genre (artwork_id,genre_id) values(?,?)";
    jdbcTemplate.batchUpdate(sql, LIST, LIST.size(), (ps, id) -> {
      ps.setLong(1, ID);
      ps.setLong(2, id);
    });
  }

  private void saveArtworkMediums() {
    ArtworkMedium artworkMedium1 = ArtworkMedium.builder().typeName("수채화").build();
    ArtworkMedium artworkMedium2 = ArtworkMedium.builder().typeName("유화").build();
    ArtworkMedium artworkMedium3 = ArtworkMedium.builder().typeName("디지털 아트").build();
    ArtworkMedium artworkMedium4 = ArtworkMedium.builder().typeName("3D 아트").build();
    ArtworkMedium artworkMedium5 = ArtworkMedium.builder().typeName("POP 아트").build();
    List<ArtworkMedium> artworkMediums = List.of(artworkMedium1, artworkMedium2, artworkMedium3, artworkMedium4,
        artworkMedium5);
    String sql = "insert into artwork_medium (id,type_name) values (?,?)";
    jdbcTemplate.batchUpdate(sql, artworkMediums, artworkMediums.size(), (ps, artworkMedium) -> {
      ps.setLong(1, artworkMediums.indexOf(artworkMedium) + 1);
      ps.setString(2, artworkMedium.getTypeName());
    });
  }

  private void saveSignatureArtworks() {
    String sql = "insert into signature_artwork ( artwork_id,display_order) values(?,?)";
    jdbcTemplate.batchUpdate(sql, LIST, LIST.size(), (ps, artworkId) -> {
      ps.setLong(1, artworkId);
      ps.setByte(2, (byte) LIST.indexOf(artworkId));
    });
  }

  private Artist createArtist(int count) {
    int year = 1990 + (count % 20);
    int month = (count % 12) + 1;
    int days = (count % 30) + 1;
    return Artist.builder()
                 .name(RandomNameGenerator.getRandomName())
                 .profileImage("test profile image " + count)
                 .birth(LocalDate.of(year, month, days))
                 .summary("test summary " + count)
                 .description("test description " + count)
                 .token(EntityToken.ARTIST.createEntityToken())
                 .build();
  }

  private List<ArtistContact> createArtistContacts() {
    ArtistContact contact1 = ArtistContact.builder()
                                          .type(ContactType.SNS)
                                          .label("instagram")
                                          .contactValue("instagramUrl")
                                          .build();
    ArtistContact contact2 = ArtistContact.builder()
                                          .type(ContactType.SNS)
                                          .label("x(twitter)")
                                          .contactValue("twitterUrl")
                                          .build();
    ArtistContact contact3 = ArtistContact.builder()
                                          .type(ContactType.SNS)
                                          .label("github")
                                          .contactValue("githubUrl")
                                          .build();
    ArtistContact contact4 = ArtistContact.builder()
                                          .type(EMAIL)
                                          .label("email")
                                          .contactValue("test@test.com")
                                          .build();
    ArtistContact contact5 = ArtistContact.builder()
                                          .type(ContactType.TEL)
                                          .label("tel")
                                          .contactValue("010-1234-5678")
                                          .build();
    return List.of(contact1, contact2, contact3, contact4, contact5);
  }

  private Artwork createArtwork(int count) {
    return Artwork.builder()
                  .name("Artwork" + count)
                  .image("Test Artwork Image" + count)
                  .description("Test Artwork Description" + count)
                  .productionYear(LocalDate.of(2000 + count, 1, 1))
                  .width(20.1 + count)
                  .height(30.5 + count)
                  .token(EntityToken.ARTWORK.identifyToken(String.valueOf(count)))
                  .build();
  }

  private Account createAccount(int count) {
    return Account.builder()
                  .email("test" + count + "@test.com")
                  .nickname("test user" + count)
                  .role(Role.USER)
                  .providerType(ProviderType.KAKAO)
                  .build();
  }

  private static class RandomNameGenerator {

    private static final List<String> lastNames = Arrays.asList(
        "김", "이", "박", "최", "한", "윤", "홍", "백", "조", "장", "추", "정",
        "허", "장", "권", "양", "한", "남궁", "제갈", "표", "고", "오", "배",
        "송", "문", "심", "안", "황보", "구", "류", "허");

    private static final List<String> firstNames = Arrays.asList(
        "원주", "민지", "재현", "동원", "창호", "정윤", "승은", "재영", "영진", "승윤", "현주", "지우", "민하", "기홍", "철", "웅", "훈", "건", "린",
        "예린", "민지", "재범", "동훈", "국", "지혜", "수지", "민기", "성준", "성주", "혜주", "지효", "지원", "형민", "용준", "병철", "규성", "나영", "유리",
        "윤아", "서윤", "서린", "수린", "무영", "동건", "동석", "승헌", "요한", "요셉", "택"
    );

    private static String getRandomName() {
      Collections.shuffle(lastNames);
      Collections.shuffle(firstNames);
      return lastNames.get(0) + firstNames.get(0);
    }
  }

}