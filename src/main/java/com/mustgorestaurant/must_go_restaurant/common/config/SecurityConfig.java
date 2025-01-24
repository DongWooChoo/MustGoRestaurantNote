package com.mustgorestaurant.must_go_restaurant.common.config;

import com.mustgorestaurant.must_go_restaurant.common.filter.CustomUsernamePasswordAuthenticationFilter;
import com.mustgorestaurant.must_go_restaurant.common.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    // 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 사용자 인증 처리를 위해 CustomUserDetailService와 PasswordEncoder를 설정
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // 인증처리를 위해 AuthenticationManager를 AuthenticationConfiguration으로 생성
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Spring Security 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // AuthenticationManager 생성
        AuthenticationManager authManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        // JSON 로그인 처리용 커스텀 필터 생성
        CustomUsernamePasswordAuthenticationFilter jsonAuthFilter = new CustomUsernamePasswordAuthenticationFilter(authManager);

        // 로그인 성공 핸들러
        jsonAuthFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"status\":\"success\",\"redirectUrl\":\"/note/myNote\"}");
        });
        // 로그인 실패 핸들러
        jsonAuthFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"status\":\"fail\",\"message\":\"아이디 또는 비밀번호가 올바르지 않습니다.\"}");
        });

        // HTTP 보안 설정
        http
                .csrf(AbstractHttpConfigurer::disable) // Todo -> CSRF토큰 사용 방안 탐색
                .authorizeHttpRequests(auth -> auth
                        // 인증 불필요
                        .requestMatchers("/user/login", "/user/signup", "/css/**", "/js/**", "/image/**").permitAll()
                        // 특정 권한 필요 경로
                        .requestMatchers("/note/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        // 기타 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                // 커스텀 JSON 로그인 필터를 UsernamePasswordAuthenticationFilter 앞에 배치
                .addFilterBefore(jsonAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
