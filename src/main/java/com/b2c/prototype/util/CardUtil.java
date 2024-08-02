package com.b2c.prototype.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public final class CardUtil {

    public static boolean isCardActive(String monthYear) {
        Date expireDate = transformMonthYearToDate(monthYear);
        if (expireDate.before(new Date())) {
            log.error("Card expired");
            throw new RuntimeException("Card expired");
        }
        return true;
    }

    private static Date transformMonthYearToDate(String monthYear) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(monthYear);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
