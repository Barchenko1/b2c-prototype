package com.b2c.prototype.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class CardUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardUtil.class);

    public static boolean isValidCreditCard(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }

        cardNumber = cardNumber.replaceAll("\\D", "");

        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }

    public static boolean isCardActive(String monthYear) {
        Date expireDate = transformMonthYearToDate(monthYear);
        if (expireDate.before(new Date())) {
            LOGGER.error("error credit_card is expired");
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
            LOGGER.error("error transformation Month/Year to Date");
            throw new RuntimeException(e);
        }
    }
}
