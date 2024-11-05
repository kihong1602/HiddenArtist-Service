package com.hiddenartist.backend.domain.search.persistence.repository;

import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import java.util.List;

public interface SearchRepository {

  List<Artist> findArtistByKeyword(String keyword);

  List<Artwork> findArtworkByKeyword(String keyword);

  List<Genre> findGenreByKeyword(String keyword);

  List<Exhibition> findExhibitionByKeyword(String keyword);

  List<Mentoring> findMentoringByKeyword(String keyword);

}