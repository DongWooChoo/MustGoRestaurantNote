package com.mustgorestaurant.must_go_restaurant.entity.note;

import com.mustgorestaurant.must_go_restaurant.entity.common.PostEntity;
import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "USER_MUST_GO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMustGo extends PostEntity {

    // 게시글 IDX
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_IDX")
    private Long postIdx;

    // 식당명
    @Column(name = "RESTAURANT_NAME", nullable = false, length = 100)
    private String restaurantName;

    // 게시글 작성자
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserInfo userInfo;

    // 게시글 생성
    public UserMustGo(String restaurantName, String restaurantInfo, String address, UserInfo userInfo) {
        super(restaurantInfo, address);
        this.restaurantName = restaurantName;
        this.userInfo = userInfo;
    }

}
