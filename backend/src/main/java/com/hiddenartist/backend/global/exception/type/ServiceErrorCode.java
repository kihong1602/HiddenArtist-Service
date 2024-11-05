package com.hiddenartist.backend.global.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServiceErrorCode {

  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Email에 부합하는 회원이 존재하지 않습니다."),
  ARTIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Artist를 찾을 수 없습니다."),
  ARTWORK_NOT_FOUND(HttpStatus.NOT_FOUND, "Artwork를 찾을 수 없습니다."),
  EXHIBITION_NOT_FOUND(HttpStatus.NOT_FOUND, "Exhibition을 찾을 수 없습니다."),
  MENTORING_NOT_FOUND(HttpStatus.NOT_FOUND, "Mentoring을 찾을 수 없습니다."),
  PROVIDER_NOT_FOUND(HttpStatus.NOT_FOUND, "OAuth2 Provider를 찾을 수 없습니다."),
  TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다. 로그인해주세요."),
  INVALID_REFRESH_TOKEN(HttpStatus.CONFLICT, "토큰이 일치하지 않습니다. 다시 로그인해주세요."),
  INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 변조되었습니다. 다시 로그인해주세요."),
  JSON_SERIALIZE_ERROR(HttpStatus.CONFLICT, "Object to JSON 변환에 실패하였습니다."),
  JSON_DESERIALIZE_ERROR(HttpStatus.CONFLICT, "JSON to Object 변환에 실패하였습니다."),
  INVALID_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "올바르지 않은 Content-Type 입니다."),
  BODY_DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "Body 데이터 형식이 올바르지 않습니다."),
  REDIS_VALUE_NOT_FOUND(HttpStatus.NOT_FOUND, "Key와 일치하는 데이터가 존재하지 않습니다."),
  UNLINK_FAIL(HttpStatus.CONFLICT, "OAuth2 연결 끊기에 실패하였습니다."),
  ACCOUNT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 탈퇴한 계정입니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근권한이 부족합니다."),
  UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다."),
  TIME_ALREADY_LOCK(HttpStatus.CONFLICT, "해당 시간은 이미 잠금 처리 되었습니다."),
  LOCK_ACQUISITION_FAILED(HttpStatus.CONFLICT, "Lock 획득에 실패하였습니다.");

  private final HttpStatus status;
  private final String errorMessage;

}