package com.hiddenartist.backend.domain.exhibition.service;

import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionDetailResponse;
import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionGetListResponse;
import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionGetPageResponse;
import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionSimpleResponse;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.domain.exhibition.persistence.repository.ExhibitionRepository;
import com.hiddenartist.backend.global.exception.type.EntityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.type.EntityToken;
import com.hiddenartist.backend.global.utils.PageUtils;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExhibitionService {

  private final ExhibitionRepository exhibitionRepository;

  @Transactional(readOnly = true)
  public ExhibitionGetPageResponse findAllExhibitions(Pageable pageable) {
    Pageable pageRequest = PageUtils.createPageRequest(pageable);
    Page<ExhibitionSimpleResponse> exhibitionSimpleResponses = exhibitionRepository.findAllExhibitions(pageRequest);
    return new ExhibitionGetPageResponse(exhibitionSimpleResponses);
  }

  @Transactional(readOnly = true)
  public ExhibitionDetailResponse findExhibitionDetail(String tokenValue) {
    String token = EntityToken.EXHIBITION.identifyToken(tokenValue);
    Exhibition exhibition = exhibitionRepository.findExhibitionByToken(token)
                                                .orElseThrow(() -> new EntityException(ServiceErrorCode.ARTIST_NOT_FOUND));
    return ExhibitionDetailResponse.convert(exhibition);
  }

  @Transactional(readOnly = true)
  public ExhibitionGetListResponse findCurrentExhibitions() {
    LocalDate now = LocalDate.now();
    List<Exhibition> currentExhibitions = exhibitionRepository.findCurrentExhibitions(now);
    List<ExhibitionSimpleResponse> exhibitionSimpleResponses = currentExhibitions.stream()
                                                                                 .map(ExhibitionSimpleResponse::convert)
                                                                                 .toList();
    return new ExhibitionGetListResponse(exhibitionSimpleResponses);
  }

  @Transactional(readOnly = true)
  public ExhibitionGetListResponse findUpcomingExhibitions() {
    LocalDate now = LocalDate.now();
    List<Exhibition> upcomingExhibitions = exhibitionRepository.findUpcomingExhibitions(now);
    List<ExhibitionSimpleResponse> exhibitionSimpleResponses = upcomingExhibitions.stream()
                                                                                  .map(ExhibitionSimpleResponse::convert)
                                                                                  .toList();
    return new ExhibitionGetListResponse(exhibitionSimpleResponses);
  }

  @Transactional(readOnly = true)
  public ExhibitionGetPageResponse findPastExhibitions(Pageable pageable) {
    LocalDate now = LocalDate.now();
    Sort sort = pageable.getSort().and(Sort.by(Sort.Order.asc("name")));
    Pageable pageRequest = PageUtils.createPageRequest(pageable, sort);
    Page<ExhibitionSimpleResponse> pastExhibitions = exhibitionRepository.findPastExhibitions(pageRequest, now);
    return new ExhibitionGetPageResponse(pastExhibitions);
  }

}