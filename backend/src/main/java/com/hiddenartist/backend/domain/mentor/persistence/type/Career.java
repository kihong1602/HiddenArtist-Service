package com.hiddenartist.backend.domain.mentor.persistence.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Career {

  JUNIOR("1~3년차"),
  MIDDLE("4~6년차"),
  SENIOR("7~9년차"),
  READER("10년차~");

  private final String description;

}