package com.mustgorestaurant.must_go_restaurant.common.constant;

public enum ResponseType {
    SUCCESS("Success"),
    SESSION_EXPIRED("The session has expired"),
    INVAILD_REQUEST("Request is not vaild");

    private final String message;

    ResponseType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
