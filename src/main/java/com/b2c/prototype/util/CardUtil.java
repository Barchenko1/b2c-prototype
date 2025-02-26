package com.b2c.prototype.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.YearMonth;

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

    public static boolean isCardActive(int monthOfExpire, int yearOfExpire) {
        YearMonth expireDate = transformMonthYearToDate(monthOfExpire, yearOfExpire);

        if (expireDate.isBefore(YearMonth.now())) {
            LOGGER.error("Error: Credit card is expired");
            throw new RuntimeException("Card expired");
        }
        return true;
    }

    private static YearMonth transformMonthYearToDate(int monthOfExpire, int yearOfExpire) {
        try {
            // Ensure month is valid (1-12)
            if (monthOfExpire < 1 || monthOfExpire > 12) {
                throw new IllegalArgumentException("Invalid month: " + monthOfExpire);
            }

            // Ensure year is valid (not in the past)
            if (yearOfExpire < LocalDate.now().getYear() % 100) { // Handling two-digit year format
                throw new IllegalArgumentException("Invalid year: " + yearOfExpire);
            }

            return YearMonth.of(2000 + yearOfExpire, monthOfExpire); // Assuming year format is YY (e.g., 24 -> 2024)
        } catch (Exception e) {
            LOGGER.error("Error: Invalid expiration date (Month: {}, Year: {})", monthOfExpire, yearOfExpire);
            throw new RuntimeException("Invalid expiration date", e);
        }
    }
}
