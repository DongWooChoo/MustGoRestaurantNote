package com.mustgorestaurant.must_go_restaurant.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // AuthenticationManager를 받아 초기화하고, 필터가 처리할 URL 설정
    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        super.setFilterProcessesUrl("/user/processLogin"); // 필터 적용 경로
    }

    // 인증 시도 메서드
    @Override
    public UsernamePasswordAuthenticationToken attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // Json 형식의 요청에 대한 처리
        if (request.getContentType() != null
                && request.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                Map<String, String> requestMap = objectMapper.readValue(request.getInputStream(), Map.class);
                String username = requestMap.getOrDefault("userId", "");
                String password = requestMap.getOrDefault("userPw", "");

                if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
                    log.warn("Username or Password is empty.");
                    username = "";
                    password = "";
                }

                UsernamePasswordAuthenticationToken authRequest =
                        new UsernamePasswordAuthenticationToken(username, password);

                return (UsernamePasswordAuthenticationToken) this.getAuthenticationManager().authenticate(authRequest);

            } catch (IOException e) {
                log.error("Failed to parse JSON login request body", e);
                return (UsernamePasswordAuthenticationToken) this.getAuthenticationManager().authenticate(
                        new UsernamePasswordAuthenticationToken("", "")
                );
            }
        }
        // Json 이외의 요청에 대한 처리
        return (UsernamePasswordAuthenticationToken) super.attemptAuthentication(request, response);
    }

    // 인증 성공 처리 메서드
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        // SecurityContext 생성 및 인증 정보 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        // SecurityContext를 HttpSessionSecurityContextRepository를 통해 관리
        HttpSessionSecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
        securityContextRepository.saveContext(context, request, response);

        log.info("Authentication successful for user: {}", authResult.getName());

        // 부모 클래스의 기본 동작 유지
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
