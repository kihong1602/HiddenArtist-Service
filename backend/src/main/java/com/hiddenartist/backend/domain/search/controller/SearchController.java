package com.hiddenartist.backend.domain.search.controller;

import com.hiddenartist.backend.domain.search.controller.response.ArtistSearchResponse;
import com.hiddenartist.backend.domain.search.controller.response.ArtworkSearchResponse;
import com.hiddenartist.backend.domain.search.controller.response.ExhibitionSearchResponse;
import com.hiddenartist.backend.domain.search.controller.response.GenreSearchResponse;
import com.hiddenartist.backend.domain.search.controller.response.MentoringSearchResponse;
import com.hiddenartist.backend.domain.search.controller.response.SearchAllResponse;
import com.hiddenartist.backend.domain.search.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

  private final SearchService searchService;

  @GetMapping("/{keyword}")
  public SearchAllResponse searchKeyword(@PathVariable("keyword") String keyword) {
    return searchService.searchAllByKeyword(keyword);
  }

  // Genre 제외 모두 Pagination 적용 필요

  @GetMapping("/artists/{keyword}")
  public List<ArtistSearchResponse> searchArtistByKeyword(@PathVariable("keyword") String keyword) {
    return searchService.searchArtistByKeyword(keyword);
  }

  @GetMapping("/artworks/{keyword}")
  public List<ArtworkSearchResponse> searchArtworkByKeyword(@PathVariable("keyword") String keyword) {
    return searchService.searchArtworkByKeyword(keyword);
  }

  @GetMapping("/exhibitions/{keyword}")
  public List<ExhibitionSearchResponse> searchExhibitionByKeyword(@PathVariable("keyword") String keyword) {
    return searchService.searchExhibitionByKeyword(keyword);
  }

  @GetMapping("/genres/{keyword}")
  public List<GenreSearchResponse> searchGenreByKeyword(@PathVariable("keyword") String keyword) {
    return searchService.searchGenreByKeyword(keyword);
  }

  @GetMapping("/mentorings/{keyword}")
  public List<MentoringSearchResponse> searchMentoringByKeyword(@PathVariable("keyword") String keyword) {
    return searchService.searchMentoringByKeyword(keyword);
  }

}