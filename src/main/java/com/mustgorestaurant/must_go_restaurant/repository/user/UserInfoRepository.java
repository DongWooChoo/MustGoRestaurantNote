package com.mustgorestaurant.must_go_restaurant.repository.user;

import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

}
