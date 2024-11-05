package com.hiddenartist.backend.global.security.service;

import com.hiddenartist.backend.global.exception.type.SecurityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class KakaoOAuth2Unlink implements OAuth2Unlink {

  private static final String UNLINK_URL = "https://kapi.kakao.com/v1/user/unlink";
  private final RestClient restClient;

  @Value("${secret.kakao-admin}")
  private String adminKey;

  private static MultiValueMap<String, String> createBody(String value) {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("target_id_type", "user_id");
    body.add("target_id", value);
    return body;
  }

  @Override
  public boolean unlink(String value) {
    final String authorization = "KakaoAK " + adminKey;

    MultiValueMap<String, String> body = createBody(value);

    Response response = restClient.post()
                                  .uri(UNLINK_URL)
                                  .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                  .header(HttpHeaders.AUTHORIZATION, authorization)
                                  .body(body)
                                  .retrieve()
                                  .body(Response.class);

    if (Objects.nonNull(response) && Objects.equals(response.id(), value)) {
      return true;
    }
    throw new SecurityException(ServiceErrorCode.BODY_DATA_NOT_FOUND);
  }

  private record Response(
      String id
  ) {

  }
}