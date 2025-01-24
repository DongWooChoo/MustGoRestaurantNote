package com.mustgorestaurant.must_go_restaurant.controller.user;

import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import com.mustgorestaurant.must_go_restaurant.repository.user.UserInfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    void setupTestUser() {
        // 테스트용 사용자 데이터 생성
        UserInfo testUser = new UserInfo("ddd123", passwordEncoder.encode("ddd123"), "ddd123@naver.com");
        userInfoRepository.save(testUser);
    }

    // 테스트 케이스 : 로그인 성공 후 메인 페이지 요청 성공
    @Test
    void whenValidLogin_thenMainPageLoad() throws Exception {

        String loginRequest = """
        {
            "userId": "ddd123",
            "userPw": "ddd123"
        }
        """;

        // 1) 로그인 요청
        MvcResult loginResult = mockMvc.perform(post("/user/processLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andReturn();

        // 2) 쿠키 가져오기
        var sessionCookie = loginResult.getResponse().getCookie("SESSION");

        // 4) 두 번째 요청에 쿠키를 넣어 인증 유지
        mockMvc.perform(get("/note/myNote")
                        .cookie(sessionCookie))
                .andExpect(status().isOk());
    }

    // 테스트 케이스 : 로그인 실패
    @Test
    void whenInvalidPassword_thenLoginFails() throws Exception {
        String loginRequest = """
        {
            "userId": "ddd123",
            "userPw": "wrongPassword"
        }
        """;

        // 잘못된 비밀번호로 로그인 요청 수행
        mockMvc.perform(post("/user/processLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk()) // 401 Unauthorized 반환
                .andExpect(content().json("{\"status\":\"fail\",\"message\":\"아이디 또는 비밀번호가 올바르지 않습니다.\"}")); // 실패 메시지 확인
    }
}
