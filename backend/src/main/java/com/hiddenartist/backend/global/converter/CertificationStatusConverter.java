package com.hiddenartist.backend.global.converter;

import com.hiddenartist.backend.domain.mentor.persistence.type.CertificationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CertificationStatusConverter implements AttributeConverter<CertificationStatus, String> {

  @Override
  public String convertToDatabaseColumn(CertificationStatus certificationStatus) {
    return certificationStatus.name();
  }

  @Override
  public CertificationStatus convertToEntityAttribute(String s) {
    return CertificationStatus.valueOf(s);
  }
}