package com.hiddenartist.backend.domain.admin.controller.request;

import com.hiddenartist.backend.domain.artist.controller.response.ArtistResponse;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AdminArtistSimpleResponse extends ArtistResponse {

  LocalDateTime createDate;
  LocalDateTime updateDate;
  LocalDateTime deleteDate;

  public AdminArtistSimpleResponse(String name, String token, String image,
      LocalDateTime createDate, LocalDateTime updateDate, LocalDateTime deleteDate) {
    super(name, token, image);
    this.createDate = createDate;
    this.updateDate = updateDate;
    this.deleteDate = deleteDate;
  }

  public static AdminArtistSimpleResponse convert(Artist artist) {
    return new AdminArtistSimpleResponse(artist.getName(), artist.getToken(), artist.getProfileImage(),
        artist.getCreateDate(), artist.getUpdateDate(), artist.getDeleteDate());
  }

}