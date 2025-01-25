package com.mustgorestaurant.must_go_restaurant.common.adapter;

import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.List;

// 스프링 Security의 인증 체계와 애플리케이션의 사용자 엔티티(UserInfo)를 연결하는 역할을 수행하는 클래스
public class UserDetailsAdapter extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    private UserInfo userInfo;

    public UserDetailsAdapter(UserInfo userInfo) {
        super(userInfo.getUserId(), userInfo.getUserPw(), userInfo.isAdmin() ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN")) : List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
