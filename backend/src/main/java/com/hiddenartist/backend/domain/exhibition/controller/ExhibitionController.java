package com.hiddenartist.backend.domain.exhibition.controller;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionDetailResponse;
import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionGetListResponse;
import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionGetPageResponse;
import com.hiddenartist.backend.domain.exhibition.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exhibitions")
@RequiredArgsConstructor
public class ExhibitionController {

  private final ExhibitionService exhibitionService;

  @GetMapping
  public ExhibitionGetPageResponse getAllExhibitions(
      @PageableDefault(page = 1, size = 16, sort = {"endDate", "name"}, direction = ASC) Pageable pageable) {
    return exhibitionService.findAllExhibitions(pageable);
  }

  @GetMapping("/{token}/details")
  public ExhibitionDetailResponse getExhibitionDetail(@PathVariable("token") String token) {
    return exhibitionService.findExhibitionDetail(token);
  }

  @GetMapping("/current")
  public ExhibitionGetListResponse getCurrentExhibitions() {
    return exhibitionService.findCurrentExhibitions();
  }

  @GetMapping("/upcoming")
  public ExhibitionGetListResponse getUpcomingExhibitions() {
    return exhibitionService.findUpcomingExhibitions();
  }

  @GetMapping("/past")
  public ExhibitionGetPageResponse getPastExhibitions(
      @PageableDefault(page = 1, size = 16, sort = "endDate", direction = DESC) Pageable pageable) {
    return exhibitionService.findPastExhibitions(pageable);
  }

}