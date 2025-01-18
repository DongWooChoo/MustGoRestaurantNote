package com.mustgorestaurant.must_go_restaurant.common.utils;

import java.time.Duration;

public class ConvertUtil {
    // Duration 객체를 받아 실행 시간을 "HH:mm:ss SSS" 형식으로 반환

    public static String durationToHHMMSS(Duration duration) {
        long hours = duration.toHoursPart();       // 시
        long minutes = duration.toMinutesPart();  // 분
        long seconds = duration.toSecondsPart();  // 초
        long millis = duration.toMillisPart();    // 밀리초

        return String.format("%02d:%02d:%02d %03d", hours, minutes, seconds, millis);
    }
}
