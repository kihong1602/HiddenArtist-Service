package com.hiddenartist.backend.global.converter;

import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringApplicationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MentoringApplicationStatusConverter implements AttributeConverter<MentoringApplicationStatus, String> {

  @Override
  public String convertToDatabaseColumn(MentoringApplicationStatus mentoringApplicationStatus) {
    return mentoringApplicationStatus.name();
  }

  @Override
  public MentoringApplicationStatus convertToEntityAttribute(String s) {
    return MentoringApplicationStatus.valueOf(s);
  }
}
