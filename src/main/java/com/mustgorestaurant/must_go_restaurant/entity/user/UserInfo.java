package com.mustgorestaurant.must_go_restaurant.entity.user;
import com.mustgorestaurant.must_go_restaurant.entity.common.CreateAndModifyDateTimeEntity;
import com.mustgorestaurant.must_go_restaurant.entity.note.UserMustGo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER_INFO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends CreateAndModifyDateTimeEntity {
    // 칼럼명 수정, length 추가, name 추가, unique추가, 날짜 타입 매핑. default값 추가, 관계 매핑(양방향 매핑의 규칙 연관관계의 주인 p209), 다중키 속성

    // 사용자 ID
    @Id
    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    // 사용자 비밀번호
    @Column(name = "USER_PW", nullable = false, length = 100)
    private String userPw;

    // 이메일
    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    // 계정 삭제 여부
    @Column(name = "IS_DELETED", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted = false;

    // 계정 삭제 일시
    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    // 관리자 여부
    @Column(name = "IS_ADMIN", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isAdmin = false;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRelation> userRelations;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserMustGo> userMustGoList;

    // 일반 사용자 생성
    public UserInfo(String userId, String userPw, String email) {
        this.userId = userId;
        this.userPw = userPw;
        this.email = email;
    }

    // 관리자 계정 생성
    public UserInfo(String userId, String userPw, String email, boolean isAdmin) {
        this.userId = userId;
        this.userPw = userPw;
        this.email = email;
        this.isAdmin = isAdmin;
    }
}

