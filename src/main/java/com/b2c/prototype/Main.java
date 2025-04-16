package com.b2c.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

//    check if all the parentheses closed properly in a string, like "([)]{[[}]})”
    private static Map<String, String> map = new HashMap<>(){{
        put("(", ")");
        put("[", "]");
        put("{", "}");
    }};

    public static void main(String[] args) {
        int[] input = {-1, -2, -3, 2};
        List<Integer> result1 = processInputIntegers1(input);
        List<Integer> result2 = processInputIntegers(input);
        System.out.println(result1);
        System.out.println(result2);
    }

    public static List<Integer> processInputIntegers1(int[] array) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                continue;
            }
            if (array[i] < 0) {
                result.add(array[i]);
            } else if (array[i] > 0) {
                int indexToRemove = array[i] - 1;
                if (indexToRemove >= 0 && indexToRemove < result.size()) {
                    result.remove(indexToRemove);
                }
            }
        }

        return result;
    }

    public static List<Integer> processInputIntegers(int[] array) {
        List<Integer> toResultList = new ArrayList<>();
        Set<Integer> toSkipSet = new HashSet<>();
        int pointer = 0;
        //
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                continue;
            }
            if (array[i] < 0) {
                toResultList.add(array[i]);
                pointer++;
            } else if (array[i] > 0) {
                int indexToRemove = array[i] - 1;
                if (indexToRemove >= 0 && indexToRemove < pointer) {
                    toSkipSet.add(indexToRemove);
                }
            }
        }

        List<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < toResultList.size(); i++) {
            if (!toSkipSet.contains(i)) {
                resultList.add(toResultList.get(i));
            }
        }

        return resultList;
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
