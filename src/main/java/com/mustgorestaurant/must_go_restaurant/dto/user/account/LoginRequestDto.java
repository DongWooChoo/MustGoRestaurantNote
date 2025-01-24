package com.mustgorestaurant.must_go_restaurant.dto.user.account;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String userId;
    private String userPw;
}
