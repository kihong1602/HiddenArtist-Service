package com.hiddenartist.backend.domain.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.hiddenartist.backend.domain.account.controller.response.AccountGetDetailResponse;
import com.hiddenartist.backend.domain.account.controller.response.AccountGetSimpleResponse;
import com.hiddenartist.backend.domain.account.controller.response.FollowArtistGetListResponse;
import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.repository.AccountRepository;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.global.exception.type.EntityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.type.EntityToken;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountService accountService;

  @Test
  @DisplayName("Email을 통해 AccountGetSimpleResponse 반환")
  void getAccountSimpleInfoTest() {
    //given
    String email = "test@test.com";
    String nickname = "test user";
    String profileImage = "test_image_url";
    Account account = Account.builder().email(email).nickname(nickname).profileImage(profileImage).build();
    given(accountRepository.findByEmail(email)).willReturn(Optional.of(account));
    //when
    AccountGetSimpleResponse accountSimpleInfo = accountService.getAccountSimpleInfo(email);

    //then
    assertThat(accountSimpleInfo).isNotNull()
                                 .extracting("nickname", "profileImage")
                                 .containsExactly(nickname, profileImage);
  }


  // 상세 회원 정보 조회
  @Test
  @DisplayName("Email을 통해 AccountGetDetailResponse 반환")
  void getAccountDetailInfoTest() {
    //given
    String email = "test@test.com";
    String nickname = "test user";
    String profileImage = "test_image_url";
    Account account = Account.builder()
                             .email(email)
                             .nickname(nickname)
                             .profileImage(profileImage)
                             .build();
    given(accountRepository.findByEmail(email)).willReturn(Optional.of(account));
    //when
    AccountGetDetailResponse accountDetailInfo = accountService.getAccountDetailInfo(email);

    //then
    assertThat(accountDetailInfo).isNotNull()
                                 .extracting("nickname", "profileImage", "email")
                                 .containsExactly(nickname, profileImage, email);
  }

  // 팔로우한 작가 조회
  @Test
  @DisplayName("Email을 통해 Follow Artist 목록 조회")
  void getFollowArtistsTest() {
    //given
    String email = "test@test.com";
    List<Artist> artists = IntStream.rangeClosed(1, 5).mapToObj(this::createArtist).toList();
    given(accountRepository.findFollowArtistListByEmail(email)).willReturn(artists);
    //when
    FollowArtistGetListResponse followArtists = accountService.getFollowArtists(email);

    //then
    assertThat(followArtists).isNotNull();
    assertThat(followArtists.artists()).hasSize(5)
                                       .extracting("name", "image", "summary", "token")
                                       .containsExactly(
                                           tuple("Artist1", "Artist_image_url1", "Artist Summary1", "1"),
                                           tuple("Artist2", "Artist_image_url2", "Artist Summary2", "2"),
                                           tuple("Artist3", "Artist_image_url3", "Artist Summary3", "3"),
                                           tuple("Artist4", "Artist_image_url4", "Artist Summary4", "4"),
                                           tuple("Artist5", "Artist_image_url5", "Artist Summary5", "5")
                                       );
  }

  private Artist createArtist(int count) {
    return Artist.builder()
                 .name("Artist" + count)
                 .profileImage("Artist_image_url" + count)
                 .summary("Artist Summary" + count)
                 .token(EntityToken.ARTIST.identifyToken(String.valueOf(count)))
                 .build();
  }

  @Test
  @DisplayName("Email에 맞는 회원이 없을 시 EntityException 발생")
  void findAccountByDetailFailureTest() {
    //given
    given(accountRepository.findByEmail(anyString())).willReturn(Optional.empty());

    //when then
    assertThatThrownBy(() -> accountService.getAccountSimpleInfo("test@test.com"))
        .isInstanceOf(EntityException.class)
        .hasMessage(ServiceErrorCode.USER_NOT_FOUND.getErrorMessage());
  }

}