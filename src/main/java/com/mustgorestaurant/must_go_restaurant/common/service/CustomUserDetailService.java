package com.mustgorestaurant.must_go_restaurant.common.service;

import com.mustgorestaurant.must_go_restaurant.common.adapter.UserDetailsAdapter;
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

        // Custom UserDetailsAdapter를 사용하여 객체를 만들어 반환
        return new UserDetailsAdapter(userInfo);
    }
}
