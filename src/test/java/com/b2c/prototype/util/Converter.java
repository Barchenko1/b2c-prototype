package com.b2c.prototype.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public final class Converter {
    public static LocalDateTime getLocalDateTime(String date) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, f);
    }

    public static Date getDate(String date) {
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(
                LocalDate.parse(date).atStartOfDay(zone).toInstant());
    }

    public static long getDurationInMinutes(String startLocalDateTime, String endLocalDateTime) {
        LocalDateTime start = LocalDateTime.parse(startLocalDateTime);
        LocalDateTime end   = LocalDateTime.parse(endLocalDateTime);

        Duration duration = Duration.between(start, end);
        return ChronoUnit.MINUTES.between(start, end);
    }

    public static LocalDateTime getEndLocalDateTime(String startLocalDateTime, long durationInMinutes) {
        LocalDateTime start = LocalDateTime.parse(startLocalDateTime);
        Duration duration = Duration.ofMinutes(durationInMinutes);
        return start.plus(duration);
    }
}
