package com.hiddenartist.backend.domain.search.service;

import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.domain.search.controller.response.ArtistSearchResponse;
import com.hiddenartist.backend.domain.search.controller.response.ArtworkSearchResponse;
import com.hiddenartist.backend.domain.search.controller.response.ExhibitionSearchResponse;
import com.hiddenartist.backend.domain.search.controller.response.GenreSearchResponse;
import com.hiddenartist.backend.domain.search.controller.response.MentoringSearchResponse;
import com.hiddenartist.backend.domain.search.controller.response.SearchAllResponse;
import com.hiddenartist.backend.domain.search.persistence.repository.SearchRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchService {

  private final SearchRepository searchRepository;
  private final AsyncSearchService asyncSearchService;

  @Transactional(readOnly = true)
  public SearchAllResponse searchAllByKeyword(String keyword) {
    CompletableFuture<List<Artist>> artistSearch = asyncSearchService.searchArtistByKeyword(keyword);
    CompletableFuture<List<Artwork>> artworkSearch = asyncSearchService.searchArtworkByKeyword(keyword);
    CompletableFuture<List<Exhibition>> exhibitionSearch = asyncSearchService.searchExhibitionByKeyword(keyword);

    CompletableFuture.allOf(artistSearch, artworkSearch, exhibitionSearch).join();

    List<Artist> artists = artistSearch.join();
    List<Artwork> artworks = artworkSearch.join();
    List<Exhibition> exhibitions = exhibitionSearch.join();

    return SearchAllResponse.create(artists, artworks, exhibitions);
  }

  @Transactional(readOnly = true)
  public List<ArtistSearchResponse> searchArtistByKeyword(String keyword) {
    List<Artist> artists = searchRepository.findArtistByKeyword(keyword);
    return artists.stream().map(ArtistSearchResponse::create).toList();
  }

  @Transactional(readOnly = true)
  public List<ArtworkSearchResponse> searchArtworkByKeyword(String keyword) {
    List<Artwork> artworks = searchRepository.findArtworkByKeyword(keyword);
    return artworks.stream().map(ArtworkSearchResponse::create).toList();
  }

  @Transactional(readOnly = true)
  public List<ExhibitionSearchResponse> searchExhibitionByKeyword(String keyword) {
    List<Exhibition> exhibitions = searchRepository.findExhibitionByKeyword(keyword);
    return exhibitions.stream().map(ExhibitionSearchResponse::create).toList();
  }

  @Transactional(readOnly = true)
  public List<GenreSearchResponse> searchGenreByKeyword(String keyword) {
    List<Genre> genres = searchRepository.findGenreByKeyword(keyword);
    return genres.stream().map(GenreSearchResponse::create).toList();
  }

  @Transactional(readOnly = true)
  public List<MentoringSearchResponse> searchMentoringByKeyword(String keyword) {
    List<Mentoring> mentorings = searchRepository.findMentoringByKeyword(keyword);
    return mentorings.stream().map(MentoringSearchResponse::create).toList();
  }

}