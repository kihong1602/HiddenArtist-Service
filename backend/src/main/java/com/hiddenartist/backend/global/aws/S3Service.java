package com.hiddenartist.backend.global.aws;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

//@Service
@RequiredArgsConstructor
public class S3Service {

  private final S3Operations s3Operations;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String BUCKET;

  public String upload(MultipartFile multipartFile, String key) throws IOException {
    try (InputStream is = multipartFile.getInputStream()) {
      S3Resource upload = s3Operations.upload(BUCKET, key, is,
          ObjectMetadata.builder().contentType(multipartFile.getContentType()).build());

      return upload.getURL().toString();
    }
  }

  public void delete(String s3Url) {
    s3Operations.deleteObject(s3Url);
  }

}