package com.b2c.prototype.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class StringFormatConverterTest {

    @Test
    void testFirstLetterToUpperCaseOtherLower() {
        assertEquals("", StringFormatConverter.firstLetterToUpperCaseOtherLower(""));
        assertNull(StringFormatConverter.firstLetterToUpperCaseOtherLower(null));

        assertEquals("Test", StringFormatConverter.firstLetterToUpperCaseOtherLower("test"));
        assertEquals("Test", StringFormatConverter.firstLetterToUpperCaseOtherLower("TEST"));
        assertEquals("Test", StringFormatConverter.firstLetterToUpperCaseOtherLower("TeSt"));

        assertEquals("Hello world", StringFormatConverter.firstLetterToUpperCaseOtherLower("hello world"));
    }

    @Test
    void testEveryFirstLetterToUpperCaseOtherLower() {
        assertEquals("", StringFormatConverter.everyFirstLetterToUpperCaseOtherLower(""));
        assertNull(StringFormatConverter.everyFirstLetterToUpperCaseOtherLower(null));

        assertEquals("Test", StringFormatConverter.everyFirstLetterToUpperCaseOtherLower("test"));
        assertEquals("Test", StringFormatConverter.everyFirstLetterToUpperCaseOtherLower("TEST"));

        assertEquals("Hello World", StringFormatConverter.everyFirstLetterToUpperCaseOtherLower("hello world"));
        assertEquals("Hello World", StringFormatConverter.everyFirstLetterToUpperCaseOtherLower("HELLO WORLD"));

        assertEquals("Cats and Dogs", StringFormatConverter.everyFirstLetterToUpperCaseOtherLower("CATS AND DOGS"));
        assertEquals("Cars and Bikes", StringFormatConverter.everyFirstLetterToUpperCaseOtherLower("CARS AND BIKES"));
    }

    @Test
    void testAllLettersToUpperCase() {
        assertEquals("", StringFormatConverter.allLettersToUpperCase(""));
        assertNull(StringFormatConverter.allLettersToUpperCase(null));

        assertEquals("TEST", StringFormatConverter.allLettersToUpperCase("test"));
        assertEquals("TEST", StringFormatConverter.allLettersToUpperCase("Test"));

        assertEquals("HELLO WORLD", StringFormatConverter.allLettersToUpperCase("Hello World"));
        assertEquals("HELLO WORLD", StringFormatConverter.allLettersToUpperCase("hello world"));
    }
}
