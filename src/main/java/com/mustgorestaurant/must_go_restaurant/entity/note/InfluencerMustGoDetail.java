package com.mustgorestaurant.must_go_restaurant.entity.note;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "INFLUENCER_MUST_GO_DETAIL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfluencerMustGoDetail{

    // 게시글 IDX
    @Id
    @OneToOne
    @JoinColumn(name = "POST_IDX", nullable = false)
    private InfluencerMustGo postIdx;

    // 식당명
    @Column(name = "RESTAURANT_NAME", nullable = false, length = 100)
    private String restaurantName;

    // 식당 유형 (한식, 일식 ...)
    @Column(name = "RESTAURANT_TYPE", nullable = false, length = 100)
    private String restaurantType;
}
