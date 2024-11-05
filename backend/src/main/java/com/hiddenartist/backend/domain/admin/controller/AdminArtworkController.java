package com.hiddenartist.backend.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/artworks")
@RequiredArgsConstructor
public class AdminArtworkController {

  // 작품 생성
  @PostMapping
  public void createArtwork() {

  }

  // 작품 수정
  @PutMapping("/{token}")
  public void updateArtwork(@PathVariable("token") String token) {

  }

  // 작품 삭제
  @DeleteMapping("/{token}")
  public void deleteArtwork(@PathVariable("token") String token) {

  }

}