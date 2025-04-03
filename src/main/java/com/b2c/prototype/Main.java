package com.b2c.prototype;

import java.util.HashMap;
import java.util.Map;

public class Main {

//    check if all the parentheses closed properly in a string, like "([)]{[[}]})”
    private static Map<String, String> map = new HashMap<>(){{
        put("(", ")");
        put("[", "]");
        put("{", "}");
    }};

    public static void main(String[] args) {
        String[] array = new String[] {"(","[",")","]","{","[","[","}","]","]",")"};
        System.out.println(isAllClosed(array));
        String[] array2 = new String[] {"(","[","]",")"};
        System.out.println(isAllClosed(array2));
    }

    private static boolean isAllClosed(String[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            String head = array[i];
            String tail = array[array.length - 1 - i];
            String closeValue = map.get(head);
            if (!tail.equals(closeValue)) {
                return false;
            }

        }
        return true;
    }

}
