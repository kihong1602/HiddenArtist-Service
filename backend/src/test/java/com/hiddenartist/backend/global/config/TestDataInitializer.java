package com.hiddenartist.backend.global.config;

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
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestDataInitializer {

  private final JdbcTemplate jdbcTemplate;

  public TestDataInitializer(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void saveAccounts(int size) {
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

  public void saveArtists(int size) {
    saveArtist(size);
    saveArtistContact();
    saveGenre();
    saveArtistGenre();
  }

  public void saveArtworks(int size) {
    saveArtworkMediums();
    saveArtwork(size);
    saveArtworkGenre();
  }

  public void saveFollowArtists(int accountSize) {
    List<Long> accountIds = LongStream.rangeClosed(1L, accountSize).boxed().toList();
    String sql = "insert into follow_artist (account_id,artist_id) values (?,?)";
    jdbcTemplate.batchUpdate(sql, accountIds, accountSize, (ps, accountId) -> {
      for (long artistId = 1; artistId <= accountSize - accountId + 1; artistId++) {
        ps.setLong(1, accountId);
        ps.setLong(2, artistId);
        ps.addBatch();
      }
    });
  }

  public void saveSignatureArtworks() {
    List<Long> artworkIdList = List.of(1L, 2L, 3L);
    String sql = "insert into signature_artwork ( artwork_id,display_order) values(?,?)";
    jdbcTemplate.batchUpdate(sql, artworkIdList, artworkIdList.size(), (ps, artworkId) -> {
      ps.setLong(1, artworkId);
      ps.setByte(2, (byte) artworkIdList.indexOf(artworkId));
    });
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
      ps.setLong(4, 1L);
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
    List<Long> genreIds = List.of(1L, 2L, 3L);
    String sql = "insert into artist_genre (artist_id,genre_id) values(?,?)";
    jdbcTemplate.batchUpdate(sql, genreIds, genreIds.size(), (ps, id) -> {
      ps.setLong(1, 1L);
      ps.setLong(2, id);
    });
  }

  private void saveArtworkGenre() {
    List<Long> genreIds = List.of(1L, 2L, 3L);
    String sql = "insert into artwork_genre (artwork_id,genre_id) values(?,?)";
    jdbcTemplate.batchUpdate(sql, genreIds, genreIds.size(), (ps, id) -> {
      ps.setLong(1, 1L);
      ps.setLong(2, id);
    });
  }

  private Account createAccount(int count) {
    return Account.builder()
                  .email("test" + count + "@test.com")
                  .nickname("test user" + count)
                  .role(Role.USER)
                  .providerType(ProviderType.KAKAO)
                  .build();
  }

  private Artist createArtist(int count) {
    int year = 1990 + (count % 20);
    int month = (count % 12) + 1;
    int days = (count % 30) + 1;
    return Artist.builder()
                 .name("artist" + count)
                 .profileImage("test profile image " + count)
                 .token(EntityToken.ARTIST.identifyToken(String.valueOf(count)))
                 .birth(LocalDate.of(year, month, days))
                 .summary("test summary " + count)
                 .description("test description " + count)
                 .build();
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

}