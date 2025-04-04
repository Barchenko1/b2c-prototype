package com.b2c.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

//    check if all the parentheses closed properly in a string, like "([)]{[[}]})”
    private static Map<String, String> map = new HashMap<>(){{
        put("(", ")");
        put("[", "]");
        put("{", "}");
    }};

    public static void main(String[] args) {
//        String[] array = new String[] {"(","[",")","]","{","[","[","}","]","]",")"};
//        System.out.println(isAllClosed(array));
//        String[] array2 = new String[] {"(","[","]",")"};
//        System.out.println(isAllClosed(array2));

        int[] input = {-1, -2, -3, 2};
        List<Integer> result = processInputIntegers(input);
        System.out.println(result); // Output: [-1, -3]
    }

    public static List<Integer> processInputIntegers(int[] array) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0) {
                result.add(array[i]);
            } else if (array[i] > 0) {
                int indexToRemove = array[i] - 1;
                if (indexToRemove >= 0
                        && indexToRemove < result.size()) {
                    result.remove(indexToRemove);
                }
            }
            if (array[i] == 0) {
                continue;
            }
        }

        return result;
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
