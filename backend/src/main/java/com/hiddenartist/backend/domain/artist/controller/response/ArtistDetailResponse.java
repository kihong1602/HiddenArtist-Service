package com.hiddenartist.backend.domain.artist.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artist.persistence.ArtistContact;
import com.hiddenartist.backend.domain.artist.persistence.type.ContactType;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ArtistDetailResponse extends ArtistResponse {

  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate birth;
  private List<String> genres;
  private Map<ContactType, List<ContactInfo>> contacts;
  private String summary;
  private String description;

  private ArtistDetailResponse(String name, String token, String image, LocalDate birth, List<String> genres,
      Map<ContactType, List<ContactInfo>> contacts, String summary, String description) {
    super(name, token, image);
    this.birth = birth;
    this.genres = genres;
    this.contacts = contacts;
    this.summary = summary;
    this.description = description;
  }

  public static ArtistDetailResponse create(Artist artist, List<ArtistContact> artistContacts, List<Genre> genres) {
    List<String> genreNames = genres.stream().map(Genre::getName).toList();
    Map<ContactType, List<ContactInfo>> contacts = createContactInfos(artistContacts);
    return new ArtistDetailResponse(artist.getName(), artist.getToken(), artist.getProfileImage(), artist.getBirth(),
        genreNames, contacts, artist.getSummary(), artist.getDescription());
  }

  private static Map<ContactType, List<ContactInfo>> createContactInfos(
      List<ArtistContact> artistContacts) {
    Map<ContactType, List<ContactInfo>> contacts =
        Arrays.stream(ContactType.values()).collect(Collectors.toMap(Function.identity(), contactType -> new ArrayList<>()));
    artistContacts.forEach(
        contact -> contacts.get(contact.getType()).add(new ContactInfo(contact.getLabel(), contact.getContactValue()))
    );
    return contacts;
  }

  public record ContactInfo(
      String label,

      String value
  ) {

  }
}