package com.mustgorestaurant.must_go_restaurant.service.user;

import com.mustgorestaurant.must_go_restaurant.repository.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public AccountService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }
}
