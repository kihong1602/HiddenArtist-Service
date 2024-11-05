package com.hiddenartist.backend.domain.search.service;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.domain.search.persistence.repository.SearchRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AsyncSearchService {

  private final SearchRepository searchRepository;

  @Async("searchTaskExecutor")
  @Transactional(readOnly = true)
  public CompletableFuture<List<Artist>> searchArtistByKeyword(String keyword) {
    return CompletableFuture.completedFuture(searchRepository.findArtistByKeyword(keyword));
  }

  @Async("searchTaskExecutor")
  @Transactional(readOnly = true)
  public CompletableFuture<List<Artwork>> searchArtworkByKeyword(String keyword) {
    return CompletableFuture.completedFuture(searchRepository.findArtworkByKeyword(keyword));
  }

  @Async("searchTaskExecutor")
  @Transactional(readOnly = true)
  public CompletableFuture<List<Exhibition>> searchExhibitionByKeyword(String keyword) {
    return CompletableFuture.completedFuture(searchRepository.findExhibitionByKeyword(keyword));
  }

}