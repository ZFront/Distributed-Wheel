package com.wheel.learn.algorithm.leetcode.stackandqueue;

import java.util.Stack;

/**
 * @desc 有效的括号
 * {@link https://leetcode-cn.com/problems/valid-parentheses/}
 * @author: zhouf
 */
public class ID20 {

    public static void main(String[] args) {
        System.out.println(isValid("[{()}]"));
        System.out.println(isValid("[{())]"));
    }

    private static Stack<Character> stack = new Stack<>();


    public static boolean isValid(String s) {
        for (Character c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                // 取出对应的数值进行匹配
                char cStack = stack.pop();
                boolean b1 = c == ')' && cStack != '(';
                boolean b2 = c == ']' && cStack != '[';
                boolean b3 = c == '}' && cStack != '{';
                if (b1 || b2 || b3) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
