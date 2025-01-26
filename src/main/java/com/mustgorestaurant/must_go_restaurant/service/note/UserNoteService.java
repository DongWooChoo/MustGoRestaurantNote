package com.mustgorestaurant.must_go_restaurant.service.note;

import com.mustgorestaurant.must_go_restaurant.common.constant.ResponseMessage;
import com.mustgorestaurant.must_go_restaurant.common.exception.DBException;
import com.mustgorestaurant.must_go_restaurant.common.exception.DuplicateValueException;
import com.mustgorestaurant.must_go_restaurant.common.exception.InvalidParameterException;
import com.mustgorestaurant.must_go_restaurant.entity.note.UserMustGo;
import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import com.mustgorestaurant.must_go_restaurant.repository.note.UserMustGoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNoteService {

    @Autowired
    private UserMustGoRepository userMustGoRepository;

    // 자신의 맛집을 저장
    public void insertNote(String restaurantName, String restaurantInfo, String address, UserInfo userInfo) {

        // 중복 검사
        if (userMustGoRepository.existsByRestaurantNameAndAddress(restaurantName, address)) {
            throw new DuplicateValueException(ResponseMessage.DUPLICATE_VALUE.getMessage());
        }

        // DB 저장 작업 진행
        try {
            userMustGoRepository.save(new UserMustGo(restaurantName, restaurantInfo, address, userInfo));
        } catch (Exception e) {
            throw new DBException(ResponseMessage.DB_OPERATION_FAILED.getMessage());
        }
    }

    // InsertNote 요청에 대해 Params이 올바른지 검사
    public void validateInsertNoteParams(String restaurantName, String restaurantInfo, String address) {
        if(restaurantName == null || restaurantInfo == null || address == null) {
            throw new InvalidParameterException(ResponseMessage.INVALID_PARAMETER.getMessage());
        }
    }
}
