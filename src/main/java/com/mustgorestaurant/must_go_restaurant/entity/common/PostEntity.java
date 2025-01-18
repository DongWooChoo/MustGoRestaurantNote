package com.mustgorestaurant.must_go_restaurant.entity.common;

import com.mustgorestaurant.must_go_restaurant.entity.influencer.InfluencerInfo;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class PostEntity extends CreateAndModifyDateTimeEntity{

    // 식당 주소
    @Column(name = "ADDRESS", nullable = false, length = 100)
    private String address;

    // 식당 정보
    @Column(name = "RESTAURANT_INFO", nullable = false, length = 1000)
    private String restaurantInfo;

}
