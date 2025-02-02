package com.mustgorestaurant.must_go_restaurant.entity.note;

import com.mustgorestaurant.must_go_restaurant.entity.common.PostEntity;
import com.mustgorestaurant.must_go_restaurant.entity.influencer.InfluencerInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "INFLUENCER_MUST_GO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfluencerMustGo extends PostEntity {

    // 게시글 IDX
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_IDX", nullable = false)
    private Long postIdx;

    // SNS 게시글 주소
    @Column(name = "SNS_POST_LINK", nullable = false, length = 100)
    private String snsPostLink;

    // SNS 등록일
    @Column(name = "SNS_UPLOAD_DATE", nullable = false)
    private LocalDateTime snsUploadDate;

    // SNS 좋아요 수
    @Column(name = "SNS_LIKE_COUNT", nullable = false)
    private Long snsLikeCount;

    // SNS 게시글 작성자
    @ManyToOne
    @JoinColumn(name = "INFLUENCER_ID", nullable = false)
    private InfluencerInfo influencerInfo;

}