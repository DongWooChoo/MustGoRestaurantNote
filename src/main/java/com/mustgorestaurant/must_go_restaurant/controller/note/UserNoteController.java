package com.mustgorestaurant.must_go_restaurant.controller.note;

import com.mustgorestaurant.must_go_restaurant.common.annotation.AuthUser;
import com.mustgorestaurant.must_go_restaurant.common.constant.ResponseMessage;
import com.mustgorestaurant.must_go_restaurant.dto.common.ResponseDto;
import com.mustgorestaurant.must_go_restaurant.dto.note.RestaurantDataDto;
import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import com.mustgorestaurant.must_go_restaurant.service.note.UserNoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/note")
@Controller
public class UserNoteController {

    @Value("${naver.map.client.id}")
    private String clientId;

    private final UserNoteService userNoteService;

    @Autowired
    public UserNoteController(UserNoteService userNoteService) {
        this.userNoteService = userNoteService;
    }

    // 나의 맛집 노트들을 보여주는 페이지 반환
    @GetMapping("/myNote")
    public String selectMyNote(Model model) {
        model.addAttribute("naverMapClientId", clientId);
        return "user/note/myNote";
    }

    // 나의 맛집 노트들을 보여주는 페이지 반환
    @PostMapping("/getMyNote")
    @ResponseBody
    public List<RestaurantDataDto> getMyNote(@AuthUser UserInfo userInfo, Model model) {
        return userNoteService.getMyNote(userInfo);
    }

    // 맛집 정보를 추가
    @PostMapping("/insertMyNote")
    public ResponseEntity<ResponseDto> insertNote(@AuthUser UserInfo userInfo, @RequestBody RestaurantDataDto restaurantDataDto) {
        // 파라미터 유효성 검사
        userNoteService.validateMyNoteServiceParams(restaurantDataDto.getRestaurantName(), restaurantDataDto.getRestaurantInfo(), restaurantDataDto.getAddress());
        // 맛집 정보 삽입 서비스 호출
        userNoteService.insertNote(restaurantDataDto.getRestaurantName(), restaurantDataDto.getRestaurantInfo(), restaurantDataDto.getAddress(), userInfo);
        return ResponseEntity.ok(new ResponseDto(ResponseMessage.SUCCESS.getMessage()));
    }

    // 맛집 정보를 수정
    @PostMapping("/updateMyNote")
    public ResponseEntity<ResponseDto> updateNote(@AuthUser UserInfo userInfo, @RequestBody RestaurantDataDto restaurantDataDto) {
        // 파라미터 유효성 검사
        userNoteService.validateMyNoteServiceParams(restaurantDataDto.getPostIdx(), restaurantDataDto.getRestaurantName(), restaurantDataDto.getRestaurantInfo(), restaurantDataDto.getAddress());
        // 맛집 정보 수정 서비스 호출
        userNoteService.updateNote(restaurantDataDto.getPostIdx(), restaurantDataDto.getRestaurantName(), restaurantDataDto.getRestaurantInfo(), restaurantDataDto.getAddress(), userInfo);
        return ResponseEntity.ok(new ResponseDto(ResponseMessage.SUCCESS.getMessage()));
    }

    // 맛집 정보를 삭제
    @PostMapping("/deleteMyNote")
    public ResponseEntity<ResponseDto> deleteNote(@AuthUser UserInfo userInfo, @RequestBody RestaurantDataDto restaurantDataDto) {
        // 파라미터 유효성 검사
        userNoteService.validateMyNoteServiceParams(restaurantDataDto.getPostIdx());
        // 맛집 정보 삭제 서비스 호출
        userNoteService.deleteNote(restaurantDataDto.getPostIdx(), userInfo);
        return ResponseEntity.ok(new ResponseDto(ResponseMessage.SUCCESS.getMessage()));
    }

}
