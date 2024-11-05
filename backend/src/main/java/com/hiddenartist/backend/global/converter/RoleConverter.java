package com.hiddenartist.backend.global.converter;

import com.hiddenartist.backend.domain.account.persistence.type.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

  @Override
  public String convertToDatabaseColumn(Role role) {
    return role.name();
  }

  @Override
  public Role convertToEntityAttribute(String s) {
    return Role.valueOf(s);
  }

}