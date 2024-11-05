package com.hiddenartist.backend.global.redis;

public interface RedisClient<V> {

  String REFRESH_TOKEN_PREFIX = "RefreshToken:";

  String MENTORING_PREFIX = "Mentoring:";

  void save(V value);

  V findBy(String key);

  void deleteBy(String key);
}