package com.hiddenartist.backend.domain.artwork.controller;

import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkDetailResponse;
import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkGetRecommendResponse;
import com.hiddenartist.backend.domain.artwork.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artworks")
@RequiredArgsConstructor
public class ArtworkController {

  private final ArtworkService artworkService;


  @GetMapping("/{token}")
  public ArtworkDetailResponse getArtworkDetail(@PathVariable("token") String token) {
    return artworkService.getArtworkDetail(token);
  }

  @GetMapping("/recommend")
  public ArtworkGetRecommendResponse getArtworkRecommend() {
    return artworkService.getArtworkRecommend();
  }

  @PostMapping("/{token}/pick")
  public void savePickArtwork(@AuthenticationPrincipal String email, @PathVariable("token") String token) {
    artworkService.savePickArtwork(email, token);
  }

}