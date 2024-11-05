package com.hiddenartist.backend.domain.admin.controller;

import com.hiddenartist.backend.domain.admin.service.AdminGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/genres")
@RequiredArgsConstructor
public class AdminGenreController {

  private final AdminGenreService genreService;

  @GetMapping("/{keyword}")
  public void findGenres(@PathVariable("keyword") String keyword) {
    
  }

}