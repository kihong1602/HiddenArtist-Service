package com.hiddenartist.backend.domain.account.persistence.repository;

import com.hiddenartist.backend.domain.account.controller.response.AccountGetMentoringApplicationResponse;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAccountRepository {

  void removeFollowArtists(String email, List<String> token);

  List<Artist> findFollowArtistListByEmail(String email);

  Page<AccountGetMentoringApplicationResponse> findMentoringApplicationByEmail(Pageable pageable, String email);

}