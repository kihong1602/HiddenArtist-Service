package com.hiddenartist.backend.global.converter;

import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MentoringStatusConverter implements AttributeConverter<MentoringStatus, String> {

  @Override
  public String convertToDatabaseColumn(MentoringStatus mentoringStatus) {
    return mentoringStatus.name();
  }

  @Override
  public MentoringStatus convertToEntityAttribute(String s) {
    return MentoringStatus.valueOf(s);
  }
}
