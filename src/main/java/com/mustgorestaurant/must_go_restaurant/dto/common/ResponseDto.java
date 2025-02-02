package com.mustgorestaurant.must_go_restaurant.dto.common;

import lombok.Data;

@Data
public class ResponseDto {
    // 응답 상태를 전달하는 DTO
    private String status;
    private String message;

    public ResponseDto(String status) {
        this.status = status;
        this.message = "";
    }

    public ResponseDto(String status, String result) {
        this.status = status;
        this.message = result;
    }
}
