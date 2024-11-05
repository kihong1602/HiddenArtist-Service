package com.hiddenartist.backend.domain.artwork.service;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.repository.AccountRepository;
import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkDetailResponse;
import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkGetRecommendResponse;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.artwork.persistence.PickArtwork;
import com.hiddenartist.backend.domain.artwork.persistence.repository.ArtworkRepository;
import com.hiddenartist.backend.domain.artwork.persistence.repository.PickArtworkRepository;
import com.hiddenartist.backend.global.exception.type.EntityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.type.EntityToken;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtworkService {

  private final ArtworkRepository artworkRepository;
  private final AccountRepository accountRepository;
  private final PickArtworkRepository pickArtworkRepository;

  @Transactional(readOnly = true)
  public ArtworkDetailResponse getArtworkDetail(String tokenValue) {
    String token = EntityToken.ARTWORK.identifyToken(tokenValue);
    return artworkRepository.findArtworkDetailByToken(token);
  }

  @Transactional(readOnly = true)
  public ArtworkGetRecommendResponse getArtworkRecommend() {
    List<Artwork> artworkRecommend = artworkRepository.findArtworkRecommend();
    return ArtworkGetRecommendResponse.create(artworkRecommend);
  }

  @Transactional
  public void savePickArtwork(String email, String tokenValue) {
    String token = EntityToken.ARTWORK.identifyToken(tokenValue);
    Account account = accountRepository.findByEmail(email)
                                       .orElseThrow(() -> new EntityException(ServiceErrorCode.USER_NOT_FOUND));
    Artwork artwork = artworkRepository.findByToken(token)
                                       .orElseThrow(() -> new EntityException(ServiceErrorCode.ARTWORK_NOT_FOUND));
    PickArtwork pickArtwork = PickArtwork.builder().account(account).artwork(artwork).build();
    pickArtworkRepository.save(pickArtwork);
  }

}