package com.hiddenartist.backend.global.converter;

import com.hiddenartist.backend.domain.payment.persistence.type.PaymentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, String> {

  @Override
  public String convertToDatabaseColumn(PaymentStatus paymentStatus) {
    return paymentStatus.name();
  }

  @Override
  public PaymentStatus convertToEntityAttribute(String s) {
    return PaymentStatus.valueOf(s);
  }
}
