package com.hiddenartist.backend.domain.artist.persistence.repository;

import com.hiddenartist.backend.domain.admin.controller.request.AdminArtistSimpleResponse;
import com.hiddenartist.backend.domain.artist.controller.response.ArtistDetailResponse;
import com.hiddenartist.backend.domain.artist.controller.response.ArtistSimpleResponse;
import com.hiddenartist.backend.domain.artist.controller.response.SignatureArtworkResponse;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomArtistRepository {

  Page<ArtistSimpleResponse> findAllArtists(Pageable pageable);

  Page<AdminArtistSimpleResponse> findAllArtistsForAdmin(Pageable pageable);

  ArtistDetailResponse findArtistDetailByToken(String token);

  List<Artist> findPopularArtists();

  List<Artist> findNewArtists();

  List<SignatureArtworkResponse> findSignatureArtworkByToken(String token);

  List<Artwork> findAllArtworkByToken(String token);

}