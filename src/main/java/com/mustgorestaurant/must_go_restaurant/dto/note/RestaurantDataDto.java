package com.mustgorestaurant.must_go_restaurant.dto.note;

import lombok.Data;

@Data
public class RestaurantDataDto {

    long postIdx;
    String restaurantName;
    String restaurantInfo;
    String address;

    public RestaurantDataDto(long postIdx, String restaurantName, String restaurantInfo, String address) {
        this.postIdx = postIdx;
        this.restaurantName = restaurantName;
        this.restaurantInfo = restaurantInfo;
        this.address = address;
    }
}
