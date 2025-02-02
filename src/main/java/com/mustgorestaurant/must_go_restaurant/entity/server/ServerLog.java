package com.mustgorestaurant.must_go_restaurant.entity.server;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "SERVER_LOG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerLog {

    // 로그 IDX
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_IDX")
    private Long logIdx;

    // 접속 사용자 IP
    @Column(name = "REQUEST_IP", length = 50)
    private String requestIp;

    // 요청 URL
    @Column(name = "REQUEST_METHOD", length = 1000)
    private String requestMethod;

    // 요청 URL
    @Column(name = "REQUEST_URL", length = 1000)
    private String requestUrl;

    // 요청 파라미터
    @Column(name = "REQUEST_PARAMS", length = 1000)
    private String requestParams;

    // 요청 일시
    @Column(name = "REQUEST_TIME")
    private LocalDateTime requestTime;

    // 응답 상태
    @Column(name = "RESPONSE_STATUS")
    private Long responseStatus;

    // 응답 코멘트
    @Column(name = "RESPONSE_COMMENT", length = 1000)
    private String responseComment;

    // 응답 시간
    @Column(name = "RESPONSE_TIME")
    private LocalDateTime responseTime;

    // 실행 시간
    @Column(name = "EXECUTION_TIME")
    private String executionTime;

    public ServerLog(String requestIp, String requestMethod, String requestUrl, String requestParams, LocalDateTime requestTime) {
        this.requestIp = requestIp;
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.requestParams = requestParams;
        this.requestTime = requestTime;
    }
}
