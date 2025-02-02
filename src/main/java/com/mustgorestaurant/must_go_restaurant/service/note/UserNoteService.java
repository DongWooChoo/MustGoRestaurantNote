package com.mustgorestaurant.must_go_restaurant.service.note;

import com.mustgorestaurant.must_go_restaurant.common.constant.ResponseMessage;
import com.mustgorestaurant.must_go_restaurant.common.exception.DBException;
import com.mustgorestaurant.must_go_restaurant.common.exception.DuplicateValueException;
import com.mustgorestaurant.must_go_restaurant.common.exception.InvalidParameterException;
import com.mustgorestaurant.must_go_restaurant.dto.note.RestaurantDataDto;
import com.mustgorestaurant.must_go_restaurant.entity.note.UserMustGo;
import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import com.mustgorestaurant.must_go_restaurant.repository.note.UserMustGoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserNoteService {

    @Autowired
    private UserMustGoRepository userMustGoRepository;

    // 자신의 맛집 노트를 조회
    public List<RestaurantDataDto> getMyNote(UserInfo userInfo) {
        List<UserMustGo> UserMustGoList = userMustGoRepository.findByUserInfo(userInfo);
        // DTO 리스트 생성
        List<RestaurantDataDto> RestaurantDataList = new ArrayList<>();
        for (UserMustGo entity : UserMustGoList) {
            RestaurantDataDto dto = new RestaurantDataDto(entity.getPostIdx() ,entity.getRestaurantName(), entity.getRestaurantInfo(), entity.getAddress());
            RestaurantDataList.add(dto);
        }
        return RestaurantDataList;
    }

    public void validateMyNoteServiceParams(Object... params) {
        for (Object param : params) {
            if (param == null || (param instanceof String && ((String) param).trim().isEmpty())) {
                throw new InvalidParameterException(ResponseMessage.INVALID_PARAMETER.getMessage());
            }
            if (param instanceof Long && (Long) param <= 0) {
                throw new InvalidParameterException(ResponseMessage.INVALID_PARAMETER.getMessage());
            }
        }
    }

    // 자신의 맛집을 저장
    public void insertNote(String restaurantName, String restaurantInfo, String address, UserInfo userInfo) {
        // 중복 검사
        if (userMustGoRepository.existsByRestaurantNameAndAddress(restaurantName, address)) {
            throw new DuplicateValueException(ResponseMessage.DUPLICATE_VALUE.getMessage());
        }

        // DB 저장 작업 진행
        userMustGoRepository.save(new UserMustGo(restaurantName, restaurantInfo, address, userInfo));
    }

    //자신의 맛집을 수정
    public void updateNote(long postIdx, String restaurantName, String restaurantInfo, String address, UserInfo userInfo) {
        // 해당 postIdx가 존재하는지 확인
        if (!userMustGoRepository.existsById(postIdx)) {
            throw new DBException(ResponseMessage.DATA_NOT_FOUND.getMessage());
        }
        // DB 수정 작업 진행
        int updatedCount = userMustGoRepository.updateNoteByPostIdx(postIdx, restaurantName, restaurantInfo, address, userInfo);

        // 업데이트 실패 시 예외 발생
        if (updatedCount == 0) {
            throw new DBException(ResponseMessage.DB_OPERATION_FAILED.getMessage());
        }
    }

    // 자신의 맛집을 삭제
    public void deleteNote(long postIdx, UserInfo userInfo) {
        // DB 삭제 작업 진행
        int deletedCount = userMustGoRepository.deleteAndReturnByPostIdx(postIdx, userInfo);

        // 삭제된 값이 없으면 예외 발생
        if(deletedCount == 0) {
            throw new DBException(ResponseMessage.DB_OPERATION_FAILED.getMessage());
        }
    }
}
