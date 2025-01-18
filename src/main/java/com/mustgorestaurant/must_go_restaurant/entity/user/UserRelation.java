package com.mustgorestaurant.must_go_restaurant.entity.user;
import com.mustgorestaurant.must_go_restaurant.entity.common.CreateDateTimeEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "USER_RELATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRelation extends CreateDateTimeEntity {

    // IDX
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDX")
    private Long idx;

    // 사용자 ID
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserInfo userInfo;

    // 친구 ID
    @Column(name = "FRIEND_ID", nullable = false, length = 50)
    private String friendId;

}
