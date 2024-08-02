package com.b2c.prototype.util;

public final class StringFormatConverter {

    public static String firstLetterToUpperCaseOtherLower(String input) {
        if (validateInput(input)) {
            return input;
        }
        input = input.toLowerCase();
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String everyFirstLetterToUpperCaseOtherLower(String input) {
        if (validateInput(input)) {
            return input;
        }

        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (word.equalsIgnoreCase("and")) {
                result.append("and");
            } else {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
            result.append(" ");
        }

        return result.toString().trim();
    }

    public static String allLettersToUpperCase(String input) {
        if (validateInput(input)) {
            return input;
        }
        return input.toUpperCase();
    }

    private static boolean validateInput(String input) {
        return (input == null || input.isEmpty());
    }
}
