package com.b2c.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main2 {
//    Given an expression string x. Examine whether the pairs and the orders of {,},(,),[,]
//    are correct in exp. For example, the function should return 'true' for
//    exp = {[()]} and 'false' for exp= [(]).
//    Example 1
//    Input: {([])}
//    Output: true
//    Explanation: { ([]) }. Same colored brackets can form balanced pairs, with 70 number of unbalanced bracket.
//    Example 2
//    Input: ()
//    Output: true
//    Explanation: (). Same bracket can form balanced pairs, and here only 1 type of bracket is present and in balanced way.
//            Example 3
//    Input: (}
//Output: false


    private static String exp = "{[()]}";
    private static String exp2 = "[(])";
    public static void main(String[] args) {
        System.out.println(isValid(exp));
        System.out.println(isValid(exp2));
    }

    private static boolean isValid(String array) {
        Stack<Character> stack = new Stack<>();
        for (char c : array.toCharArray()) {
            if (c == '{' || c == '[' || c == '(') {
                stack.push(c);
            } else if (c == '}' || c == ']' || c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if (!isMatching(top, c)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private static boolean isMatching(char open, char close) {
        return (open == '{' && close == '}') || (open == '[' && close == ']') || (open == '(' && close == ')');
    }
}
