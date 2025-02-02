package com.mustgorestaurant.must_go_restaurant.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreateAndModifyDateTimeEntity extends CreateDateTimeEntity{

    // 수정일
    @LastModifiedDate
    @Column(name = "MODIFIED_DATE", nullable = false)
    private LocalDateTime modifiedDate;
}
