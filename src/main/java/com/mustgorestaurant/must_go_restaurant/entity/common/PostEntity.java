package com.mustgorestaurant.must_go_restaurant.entity.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class PostEntity extends CreateAndModifyDateTimeEntity{

    // 식당 정보
    @Column(name = "RESTAURANT_INFO", nullable = false, length = 1000)
    private String restaurantInfo;

    // 식당 주소
    @Column(name = "ADDRESS", nullable = false, length = 100)
    private String address;

    // 기본 생성자
    protected PostEntity() {
    }

    // 매개변수가 있는 생성자
    public PostEntity(String restaurantInfo, String address) {
        this.restaurantInfo = restaurantInfo;
        this.address = address;
    }
}
