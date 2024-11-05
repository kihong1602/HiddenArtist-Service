package com.hiddenartist.backend.global.converter;

import com.hiddenartist.backend.domain.artist.persistence.type.ContactType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ContactTypeConverter implements AttributeConverter<ContactType, String> {

  @Override
  public String convertToDatabaseColumn(ContactType contactType) {
    return contactType.name();
  }

  @Override
  public ContactType convertToEntityAttribute(String s) {
    return ContactType.valueOf(s);
  }

}