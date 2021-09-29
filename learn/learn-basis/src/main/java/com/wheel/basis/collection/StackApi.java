package com.wheel.basis.collection;

import com.wheel.common.util.JsonUtil;

import java.util.Stack;

/**
 * @desc
 * @author: zhouf
 */
public class StackApi {

    /**
     * 栈，先进后出
     *
     * @param args
     */
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();

        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        stack.push("e");

        System.out.println(JsonUtil.toString(stack));

        // 查看，但不移除
        System.out.println(stack.peek());

        // 查看栈顶，并移除
        System.out.println(stack.pop());

        System.out.println(JsonUtil.toString(stack));
    }
}
