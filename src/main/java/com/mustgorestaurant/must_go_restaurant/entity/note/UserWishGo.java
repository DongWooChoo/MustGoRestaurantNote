package com.mustgorestaurant.must_go_restaurant.entity.note;
import com.mustgorestaurant.must_go_restaurant.common.constant.PostType;
import com.mustgorestaurant.must_go_restaurant.entity.common.CreateDateTimeEntity;
import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "USER_WISH_GO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWishGo extends CreateDateTimeEntity {

    // IDX
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDX")
    private Long idx;

    // 사용자 ID
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserInfo userInfo;

    // 게시글 IDX
    @Column(name = "POST_IDX", nullable = false)
    private Long postIdx;

    // 게시글 유형 (일반 사용자, 인플루언서 중 하나)
    @Enumerated(EnumType.STRING)
    @Column(name = "POST_TYPE", nullable = false)
    private PostType postType;

    // 방문 여부
    @Column(name = "IS_VISIT", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isVisit;

}
