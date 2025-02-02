package com.mustgorestaurant.must_go_restaurant.dto.user.account;

import lombok.Data;

@Data
public class LoginResultDto {
    public boolean isSuccess;
    public String message;

    public LoginResultDto(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
