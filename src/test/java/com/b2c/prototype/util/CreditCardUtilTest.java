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
        assertDoesNotThrow(() -> {
            boolean isActive = CardUtil.isCardActive(12, 25);
            assertTrue(isActive);
        });
    }

    @Test
    void testIsCardActive_expiredCard_shouldThrowException() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            CardUtil.isCardActive(1, 20);
        });

        assertEquals("Card expired", thrown.getMessage());
    }

    @Test
    void testIsCardActive_invalidMonthYearFormat_shouldThrowException() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            CardUtil.isCardActive(13, 99);
        });

        assertNotNull(thrown.getMessage());
    }

    @Test
    void testIsCardActive_invalidDateFormat_shouldThrowParseException() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            CardUtil.isCardActive(3, 101);
        });

        assertInstanceOf(ParseException.class, thrown.getCause());
    }
}
