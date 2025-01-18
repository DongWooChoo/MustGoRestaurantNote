package com.mustgorestaurant.must_go_restaurant.common.config;

import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import com.mustgorestaurant.must_go_restaurant.repository.user.UserInfoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initSampleData(UserInfoRepository userInfoRepository) {
        return args -> {
            // 사용자 계정 추가
            UserInfo admin = new UserInfo(
                    "admin123",
                    "admin123",
                    "admin123@naver.com",
                    true
            );
            UserInfo user1 = new UserInfo(
                    "aaa123",
                    "aaa123",
                    "aaa123@naver.com"
            );
            UserInfo user2 = new UserInfo(
                    "bbb123",
                    "bbb123",
                    "bbb123@naver.com"
            );
            UserInfo user3 = new UserInfo(
                    "ccc123",
                    "ccc123",
                    "ccc123@naver.com"
            );
            userInfoRepository.save(admin);
            userInfoRepository.save(user1);
            userInfoRepository.save(user2);
            userInfoRepository.save(user3);
        };
    }
}
