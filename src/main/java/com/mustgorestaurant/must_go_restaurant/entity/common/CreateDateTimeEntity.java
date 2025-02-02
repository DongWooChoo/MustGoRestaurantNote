package com.mustgorestaurant.must_go_restaurant.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreateDateTimeEntity {

    // 생성일
    @CreatedDate
    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    private LocalDateTime createdDate;
}
