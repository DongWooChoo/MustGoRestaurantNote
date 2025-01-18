package com.mustgorestaurant.must_go_restaurant.common.service;

import com.mustgorestaurant.must_go_restaurant.entity.server.ServerLog;
import com.mustgorestaurant.must_go_restaurant.repository.server.ServerLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoggingService {

    private final ServerLogRepository serverLogRepository;

    public LoggingService(ServerLogRepository serverLogRepository) {
        this.serverLogRepository = serverLogRepository;
    }

    // 요청 정보를 사전에 로깅하는 메서드
    public long preLoggingSave(String requestIp, String requestMethod, String requestUrl, String requestParams, LocalDateTime requestTime) {
        ServerLog preServerLog = new ServerLog();
        preServerLog.setRequestIp(requestIp);
        preServerLog.setRequestMethod(requestMethod);
        preServerLog.setRequestUrl(requestUrl);
        preServerLog.setRequestParams(requestParams);
        preServerLog.setRequestTime(requestTime);
        ServerLog savedLog = serverLogRepository.save(preServerLog);

        return savedLog.getLogIdx();
    }

    // 응답 정보를 사후에 로깅하는 메서드
    public void postLoggingSave(long logIdx, long responseStatus, String responseComment, LocalDateTime responseTime, String excutionTime) {
        Optional<ServerLog> preServerLog = serverLogRepository.findById(logIdx);
        ServerLog serverLog = preServerLog.get();
        serverLog.setResponseStatus(responseStatus);
        serverLog.setResponseComment(responseComment);
        serverLog.setResponseTime(responseTime);
        serverLog.setExecutionTime(excutionTime);

        serverLogRepository.save(serverLog);
    }
}
