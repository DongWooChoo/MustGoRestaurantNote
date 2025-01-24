package com.mustgorestaurant.must_go_restaurant.common.constant;

public enum ResponseMessage {
    SUCCESS("Success"),
    SESSION_EXPIRED("The session has expired"),
    INVALID_REQUEST("Request is not vaild"),
    INVALID_PARAMETER("Some parameter "),
    LOGIN_FAILED("Login failed");
    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
