package com.mustgorestaurant.must_go_restaurant.common.service;

import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import com.mustgorestaurant.must_go_restaurant.repository.user.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 사용자 검색
        UserInfo userInfo = userInfoRepository.findByUserId(username);
        if (userInfo == null || userInfo.isDeleted()) {
            throw new UsernameNotFoundException("User is deleted: " + username);
        }

        // 사용자 역할 설정
        String roleName = userInfo.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";

        // UserDetails 객체 생성 및 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(userInfo.getUserId())
                .password(userInfo.getUserPw())
                .authorities(roleName)
                .build();
    }
}
