package com.hiddenartist.backend.domain.artwork.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkDetailResponse;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.global.config.AbstractMySQLRepositoryTest;
import com.hiddenartist.backend.global.config.TestDataInitializer;
import com.hiddenartist.backend.global.type.EntityToken;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ArtworkRepositoryTest extends AbstractMySQLRepositoryTest {

  @Autowired
  private TestDataInitializer initializer;

  @Autowired
  private ArtworkRepository artworkRepository;

  @Test
  @DisplayName("Token 입력시 Artwork Detail 반환")
  void findArtworkDetailByTokenTest() {
    //given
    initializer.saveArtists(1);
    initializer.saveArtworks(1);

    String token = EntityToken.ARTWORK.identifyToken("1");

    //when
    ArtworkDetailResponse artworkDetailByToken = artworkRepository.findArtworkDetailByToken(token);

    //then
    assertThat(artworkDetailByToken).isNotNull()
                                    .extracting("name", "image", "width", "height", "medium", "productionYear",
                                        "description")
                                    .containsExactly("Artwork1", "Test Artwork Image1", 21.1, 31.5, "유화", 2001,
                                        "Test Artwork Description1");
    assertThat(artworkDetailByToken.getArtists()).hasSize(1)
                                                 .extracting("name", "token")
                                                 .containsExactly(tuple
                                                     ("artist1", "1")
                                                 );
  }

  @Test
  @DisplayName("추천 작품 3개 조회")
  void findArtworkRecommendTest() {
    //given
    initializer.saveArtists(1);
    initializer.saveArtworks(50);

    //when
    List<Artwork> result = artworkRepository.findArtworkRecommend();
    Set<Artwork> uniqueResult = new HashSet<>(result);

    //then
    assertThat(result).isNotNull().hasSize(uniqueResult.size());
  }

}