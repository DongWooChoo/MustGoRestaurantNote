package com.mustgorestaurant.must_go_restaurant.entity.influencer;
import com.mustgorestaurant.must_go_restaurant.entity.common.CreateDateTimeEntity;
import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "INFLUENCER_INFO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfluencerInfo extends CreateDateTimeEntity {

    // 인플루언서 SNS 계정 ID (인스타그램 비즈니스 계정만)
    @Id
    @Column(name = "INFLUENCER_ID", nullable = false, length = 50)
    private String influencerId;

    // 인플루언서 소개
    @Column(name = "INFLUENCER_INTRODUCE", length = 1000)
    private String influencerIntroduce;

    // 인플루언서 등록인
    @ManyToOne
    @JoinColumn(name = "INSERTED_USER_ID", nullable = false)
    private UserInfo insertedUser;
}