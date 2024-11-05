package com.hiddenartist.backend.global.converter;

import com.hiddenartist.backend.domain.mentor.persistence.type.Career;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CareerConverter implements AttributeConverter<Career, String> {

  @Override
  public String convertToDatabaseColumn(Career contactType) {
    return contactType.name();
  }

  @Override
  public Career convertToEntityAttribute(String s) {
    return Career.valueOf(s);
  }

}