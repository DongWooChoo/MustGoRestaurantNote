package com.mustgorestaurant.must_go_restaurant.controller.user;

import com.mustgorestaurant.must_go_restaurant.service.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@Controller
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 로그인 페이지를 반환
    @GetMapping("/login")
    public String getLoginPage() {
        return "user/account/login";
    }

    // 로그인 요청을 처리하며, Spring Security의 CustomUsernamePasswordAuthenticationFilter를 통해 JSON 형식의 로그인 요청 데이터를 인증 처리로 위임
    // 인증에 성공하면 사용자 정보를 SecurityContext와 Redis 세션에 저장하고, JSON 응답으로 성공 메시지와 리디렉션 URL 반환
    // 인증 실패 시 JSON 형식의 에러 메시지를 반환
    // @PostMapping("/processLogin")

}
