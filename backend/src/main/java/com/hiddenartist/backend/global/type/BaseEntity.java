package com.hiddenartist.backend.global.type;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Column(updatable = false, nullable = false, columnDefinition = "datetime default current_timestamp")
  private LocalDateTime createDate;

  @LastModifiedDate
  @Column(nullable = false, columnDefinition = "datetime default current_timestamp")
  private LocalDateTime updateDate;

  @Column(columnDefinition = "datetime")
  private LocalDateTime deleteDate;

  public void updateDeleteDate() {
    this.deleteDate = LocalDateTime.now();
  }

  public boolean isDeleted() {
    return Objects.nonNull(deleteDate);
  }

}