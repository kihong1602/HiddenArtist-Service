package com.hiddenartist.backend.global.converter;

import com.hiddenartist.backend.domain.payment.persistence.type.SettlementStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SettlementStatusConverter implements AttributeConverter<SettlementStatus, String> {

  @Override
  public String convertToDatabaseColumn(SettlementStatus settlementStatus) {
    return settlementStatus.name();
  }

  @Override
  public SettlementStatus convertToEntityAttribute(String s) {
    return SettlementStatus.valueOf(s);
  }
}
