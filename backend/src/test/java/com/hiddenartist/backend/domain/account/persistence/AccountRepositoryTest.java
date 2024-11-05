package com.hiddenartist.backend.domain.account.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.hiddenartist.backend.domain.account.persistence.repository.AccountRepository;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.global.config.AbstractMySQLRepositoryTest;
import com.hiddenartist.backend.global.config.TestDataInitializer;
import com.hiddenartist.backend.global.type.EntityToken;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AccountRepositoryTest extends AbstractMySQLRepositoryTest {

  private final String EMAIL = "test1@test.com";
  @Autowired
  private TestDataInitializer initializer;
  @Autowired
  private AccountRepository accountRepository;

  @BeforeEach
  void init() {
    initializer.saveAccounts(5);
    initializer.saveArtists(10);
    initializer.saveFollowArtists(5);
  }

  @Test
  @DisplayName("Follow Artist 조회 테스트")
  void getFollowArtistListTest() {
    // when
    List<Artist> artists = accountRepository.findFollowArtistListByEmail(EMAIL);

    // then
    assertThat(artists).hasSize(5)
                       .extracting("name", "token")
                       .containsExactlyInAnyOrder(
                           tuple("artist1", EntityToken.ARTIST.identifyToken("1")),
                           tuple("artist2", EntityToken.ARTIST.identifyToken("2")),
                           tuple("artist3", EntityToken.ARTIST.identifyToken("3")),
                           tuple("artist4", EntityToken.ARTIST.identifyToken("4")),
                           tuple("artist5", EntityToken.ARTIST.identifyToken("5"))
                       );
  }

  @Test
  @DisplayName("Follow Artist 삭제 테스트")
  void deleteFollowArtistsTest() {
    //given
    List<String> removeTokens = List.of(EntityToken.ARTIST.identifyToken("1"), EntityToken.ARTIST.identifyToken("3"));

    //when
    accountRepository.removeFollowArtists(EMAIL, removeTokens);
    List<Artist> artists = accountRepository.findFollowArtistListByEmail(EMAIL);

    //then
    assertThat(artists).hasSize(3)
                       .extracting("name", "token")
                       .containsExactlyInAnyOrder(
                           tuple("artist2", EntityToken.ARTIST.identifyToken("2")),
                           tuple("artist4", EntityToken.ARTIST.identifyToken("4")),
                           tuple("artist5", EntityToken.ARTIST.identifyToken("5"))
                       );
  }
}