package com.b2c.prototype.util;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreditCardUtilTest {

    @Test
    void testIsCardActive_validCard_shouldReturnTrue() {
        String validMonthYear = "12/25";
        assertDoesNotThrow(() -> {
            boolean isActive = CardUtil.isCardActive(validMonthYear);
            assertTrue(isActive);
        });
    }

    @Test
    void testIsCardActive_expiredCard_shouldThrowException() {
        String expiredMonthYear = "01/20";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            CardUtil.isCardActive(expiredMonthYear);
        });

        assertEquals("Card expired", thrown.getMessage());
    }

    @Test
    void testIsCardActive_invalidMonthYearFormat_shouldThrowException() {
        String invalidMonthYear = "13/99";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            CardUtil.isCardActive(invalidMonthYear);
        });

        assertNotNull(thrown.getMessage());
    }

    @Test
    void testIsCardActive_invalidDateFormat_shouldThrowParseException() {
        String invalidMonthYear = "invalidFormat";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            CardUtil.isCardActive(invalidMonthYear);
        });

        assertInstanceOf(ParseException.class, thrown.getCause());
    }
}