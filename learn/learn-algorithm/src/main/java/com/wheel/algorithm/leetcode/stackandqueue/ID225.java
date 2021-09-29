package com.wheel.algorithm.leetcode.stackandqueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @desc {@link https://leetcode-cn.com/problems/implement-stack-using-queues/description/}
 * @author: zhouf
 */
public class ID225 {

    public static void main(String[] args) {

    }

    private static Queue<Integer> in = new LinkedList<>();
    private static Queue<Integer> out = new LinkedList<>();

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        out.offer(x);
        while (!in.isEmpty()) {
            out.offer(in.poll());
        }
        Queue<Integer> temp = in;
        in = out;
        out = temp;
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        return in.poll();
    }

    /**
     * Get the top element.
     */
    public int top() {
        return out.peek();
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return in.isEmpty() && out.isEmpty();
    }

}
