package com.hiddenartist.backend.domain.artist.persistence;

import com.hiddenartist.backend.domain.artist.persistence.type.ContactType;
import com.hiddenartist.backend.global.converter.ContactTypeConverter;
import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistContact extends BaseEntity {

  @Convert(converter = ContactTypeConverter.class)
  private ContactType type;

  private String label;

  private String contactValue;

  @ManyToOne(fetch = FetchType.LAZY)
  private Artist artist;

  private ArtistContact(ContactType type, String label, String contactValue, Artist artist) {
    this.type = type;
    this.label = label;
    this.contactValue = contactValue;
    this.artist = artist;
  }

}