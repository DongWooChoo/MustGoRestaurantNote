package com.mustgorestaurant.must_go_restaurant.common.inteceptor;

import com.mustgorestaurant.must_go_restaurant.common.service.LoggingService;
import com.mustgorestaurant.must_go_restaurant.common.utils.ConvertUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private final LoggingService loggingService;

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final ThreadLocal<Map<String, Object>> requestData = ThreadLocal.withInitial(HashMap::new);

    public LoggingInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    // 요청 처리 전에 실행되는 메서드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        String remoteAddr = request.getRemoteAddr();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        LocalDateTime reqestTime = LocalDateTime.now();

        // 로그 정보 DB Insert
        Long logIdx = loggingService.preLoggingSave(remoteAddr, method, uri, queryString, reqestTime);

        // 로그 IDX, 요청 시간 저장
        Map<String, Object> data = requestData.get();
        data.put("requestTime", reqestTime);
        data.put("logIdx", logIdx);
        return true;
    }

    // 요청 처리 후에 실행되는 메서드
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        int status = response.getStatus();
        String responseComment = ex == null ? "" : ex.getMessage();
        LocalDateTime responseTime = LocalDateTime.now();
        Map<String, Object> data = requestData.get();
        LocalDateTime requestTime = (LocalDateTime) data.get("requestTime");
        Long logIdx = (Long) data.get("logIdx");

        Duration duration = Duration.between(requestTime, responseTime);
        String executionTime = ConvertUtil.durationToHHMMSS(duration);

        // 로그 정보 DB Update
        loggingService.postLoggingSave(logIdx, status, responseComment, responseTime, executionTime);
    }
}