package com.wheel.algorithm.leetcode.stackandqueue;

import java.util.Stack;

/**
 * @desc {@link https://leetcode-cn.com/problems/min-stack/description/}
 * @author: zhouf
 */
public class ID155 {

    public static void main(String[] args) {

    }

    private static Stack<Integer> data = new Stack<>();
    private static Stack<Integer> min = new Stack<>();
    // 定义一个最小值，用来比较
    private static Integer minNum = Integer.MAX_VALUE;


    public void push(int val) {
        data.push(val);
        minNum = Math.min(minNum, val);
        min.push(minNum);
    }

    public void pop() {
        data.pop();
        min.pop();
        minNum = min.isEmpty() ? Integer.MAX_VALUE : min.peek();
    }

    public int top() {
        return data.peek();
    }

    /**
     * 获取最小，但由于stack中不支持此操作，所以最佳的解决方案就是为新增一个min栈
     */
    public int getMin() {
        return min.peek();
    }

    /**
     * 如果是要实现最小值队列的问题，就可以把队列转换为栈来实现，然后把问题转换为最小值栈来解决。
     */
}
