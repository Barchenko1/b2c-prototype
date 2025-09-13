package com.b2c.prototype.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class Converter {
    public static LocalDateTime getLocalDateTime(String date) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, f);
    }

    public static long getDurationInMinutes(String startLocalDateTime, String endLocalDateTime) {
        LocalDateTime start = LocalDateTime.parse(startLocalDateTime);
        LocalDateTime end   = LocalDateTime.parse(endLocalDateTime);

        Duration duration = Duration.between(start, end);
        long minutes = ChronoUnit.MINUTES.between(start, end);
        return minutes;
    }

    public static LocalDateTime getEndLocalDateTime(String startLocalDateTime, long durationInMinutes) {
        LocalDateTime start = LocalDateTime.parse(startLocalDateTime);
        Duration duration = Duration.ofMinutes(durationInMinutes);
        LocalDateTime end = start.plus(duration);
        return end;
    }
}
