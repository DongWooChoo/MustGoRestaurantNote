package com.mustgorestaurant.must_go_restaurant.common.handler;

import com.mustgorestaurant.must_go_restaurant.common.constant.ResponseMessage;
import com.mustgorestaurant.must_go_restaurant.common.exception.DBException;
import com.mustgorestaurant.must_go_restaurant.common.exception.DuplicateValueException;
import com.mustgorestaurant.must_go_restaurant.dto.common.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    // DB 작업 중 발생한 에러에 대한 예외 처리
    @ExceptionHandler(DBException.class)
    public ResponseEntity<ResponseDto> handleDBException(DBException e){
        return ResponseEntity.badRequest().body(new ResponseDto(ResponseMessage.FAIL.getMessage(), e.getMessage()));
    }

    // 중복된 값으로 인해 발생한 에러에 대한 예외 처리
    @ExceptionHandler(DuplicateValueException.class)
    public ResponseEntity<ResponseDto> handleDuplicateValueException(DuplicateValueException e){
        return ResponseEntity.badRequest().body(new ResponseDto(ResponseMessage.FAIL.getMessage(), e.getMessage()));
    }
}
