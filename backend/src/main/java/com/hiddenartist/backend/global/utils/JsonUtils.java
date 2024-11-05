package com.hiddenartist.backend.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiddenartist.backend.global.exception.type.JsonException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonUtils {

  private final ObjectMapper objectMapper;

  public <T> T deserializedJsonToObject(InputStream inputStream, Class<T> clazz) {
    try {
      return objectMapper.readValue(inputStream, clazz);
    } catch (IOException e) {
      throw new JsonException(ServiceErrorCode.JSON_DESERIALIZE_ERROR);
    }
  }

  public String serializedObjectToJson(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new JsonException(ServiceErrorCode.JSON_SERIALIZE_ERROR);
    }
  }

}