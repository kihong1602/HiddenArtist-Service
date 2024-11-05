package com.hiddenartist.backend.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtils {

  public static Pageable createPageRequest(Pageable pageable) {
    return PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
  }

  public static Pageable createPageRequest(Pageable pageable, Sort sort) {
    return PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
  }

}