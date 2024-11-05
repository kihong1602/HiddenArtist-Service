package com.hiddenartist.backend.domain.artwork.persistence.repository;

import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkDetailResponse;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import java.util.List;

public interface CustomArtworkRepository {

  ArtworkDetailResponse findArtworkDetailByToken(String token);

  List<Artwork> findArtworkRecommend();

}