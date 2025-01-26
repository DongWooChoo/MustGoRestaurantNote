package com.mustgorestaurant.must_go_restaurant.controller.note;

import ch.qos.logback.core.model.Model;
import com.mustgorestaurant.must_go_restaurant.common.annotation.AuthUser;
import com.mustgorestaurant.must_go_restaurant.common.constant.ResponseMessage;
import com.mustgorestaurant.must_go_restaurant.dto.common.ResponseDto;
import com.mustgorestaurant.must_go_restaurant.dto.note.RestaurantDataDto;
import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import com.mustgorestaurant.must_go_restaurant.service.note.UserNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/note")
@Controller
public class UserNoteController {

    private final UserNoteService userNoteService;

    @Autowired
    public UserNoteController(UserNoteService userNoteService) {
        this.userNoteService = userNoteService;
    }

    // 나의 맛집 노트들을 보여주는 페이지 반환
    @GetMapping("/myNote")
    public String selectMtNote(Model model) {
        return "user/note/myNote";
    }

    // 맛집 정보를 추가
    @PostMapping("/insertMyNote")
    public ResponseEntity<ResponseDto> insertNote(@AuthUser UserInfo userInfo, @RequestBody RestaurantDataDto restaurantDataDto) {
        userNoteService.validateInsertNoteParams(restaurantDataDto.getRestaurantName(), restaurantDataDto.getRestaurantInfo(), restaurantDataDto.getAddress());
        userNoteService.insertNote(restaurantDataDto.getRestaurantName(), restaurantDataDto.getRestaurantInfo(), restaurantDataDto.getAddress(), userInfo);
        return ResponseEntity.ok(new ResponseDto(ResponseMessage.SUCCESS.getMessage()));
    }

}
