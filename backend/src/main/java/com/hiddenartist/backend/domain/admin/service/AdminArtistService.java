package com.hiddenartist.backend.domain.admin.service;

import com.hiddenartist.backend.domain.admin.controller.request.AdminArtistSimpleResponse;
import com.hiddenartist.backend.domain.artist.persistence.repository.ArtistRepository;
import com.hiddenartist.backend.global.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminArtistService {

  private final ArtistRepository artistRepository;

  @Transactional(readOnly = true)
  public PagedModel<AdminArtistSimpleResponse> getAllArtists(Pageable pageable) {
    Pageable pageRequest = PageUtils.createPageRequest(pageable);
    Page<AdminArtistSimpleResponse> result = artistRepository.findAllArtistsForAdmin(pageRequest);
    return new PagedModel<>(result);
  }

}