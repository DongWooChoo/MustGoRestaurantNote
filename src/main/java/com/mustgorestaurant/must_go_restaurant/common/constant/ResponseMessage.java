package com.mustgorestaurant.must_go_restaurant.common.constant;

public enum ResponseMessage {
    SUCCESS("Success"), // 성공
    FAIL("Fail"), // 살패
    SESSION_EXPIRED("The session has expired"), // 세션 만료
    INVALID_REQUEST("Request is not vaild"), // 유효하지 않은 요청
    INVALID_PARAMETER("Some parameter "), // 유효하지 않은 파라미터
    LOGIN_FAILED("Login failed"), // 로그인 실패
    DUPLICATE_VALUE("The value was Duplicated"), // 중복된 값이 DB에 존재
    DB_OPERATION_FAILED("DB operation failed"), // DB작업(C,R,U,D)중 문제 발생
    DATA_NOT_FOUND("The data could not be found"); // DB로부터 값을 찾을 수 없음
    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
