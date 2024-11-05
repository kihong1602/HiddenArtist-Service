package com.hiddenartist.backend.domain.admin.type;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageInfo {

  DASH_BOARD("admin/dashboard", "Admin Console"),
  LOGIN("admin/login", "Admin Login"),
  ARTIST_LIST("admin/artists/list", "작가 목록");

  private static final String VIEW_NAME = "viewName";
  private static final String TITLE = "title";
  private final String viewName;
  private final String title;

  public Map<String, String> getAttributes() {
    return new HashMap<>() {{
      put(VIEW_NAME, viewName);
      put(TITLE, title);
    }};
  }

}