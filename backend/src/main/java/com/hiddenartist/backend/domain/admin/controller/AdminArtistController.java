package com.hiddenartist.backend.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/artists")
@RequiredArgsConstructor
public class AdminArtistController {

  // 작가 생성
  @PostMapping
  public void createArtist() {
    // RequestBody에 생성할 작가 데이터 저장
  }

  // 작가 수정
  @PutMapping("/{token}")
  public void updateArtist(@PathVariable("token") String token) {
    // RequestBody에 수정할 데이터 전달
  }

  // 작가 삭제
  @DeleteMapping("/{token}")
  public void deleteArtist(@PathVariable("token") String token) {
    // soft delete
  }

}