package com.wheel.learn.algorithm.leetcode.stackandqueue;

import java.util.Stack;

/**
 * @desc 用栈实现队列
 * {@link https://leetcode-cn.com/problems/implement-queue-using-stacks/description/}
 * 栈：后进先出
 * 队列：先进先出
 * @author: zhouf
 */
public class ID232 {

    public static void main(String[] args) {
        push(5);
        push(2);
        push(1);

        System.out.println(peek());
        System.out.println(pop());
        System.out.println(empty());
    }

    private static Stack<Integer> in = new Stack<>();
    private static Stack<Integer> out = new Stack<>();

    /**
     * 将元素X推到队列的末尾
     */
    public static void push(int x) {
        in.push(x);
    }

    /**
     * 从队列的开头移除并返回元素
     */
    public static int pop() {
        in2out();
        return out.pop();
    }

    /**
     * 返回队列开头的元素
     */
    public static int peek() {
        in2out();
        return out.peek();
    }

    private static void in2out() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
    }

    /**
     * 判断是否为空
     */
    public static boolean empty() {
        return in.isEmpty() && out.isEmpty();
    }
}
