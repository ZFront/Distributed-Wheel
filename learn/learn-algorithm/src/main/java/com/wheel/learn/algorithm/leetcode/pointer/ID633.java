package com.wheel.learn.algorithm.leetcode.pointer;

/**
 * @desc {@link https://leetcode-cn.com/problems/sum-of-square-numbers/description/}
 * @author: zhouf
 */
public class ID633 {

    public static boolean judgeSquareSum(int c) {
        if (c < 0) {
            return false;
        }
        int i = 0, j = (int) Math.sqrt(c);
        while (i < j) {
            int sum = i * i + j * j;
            if (sum > c) {
                j--;
            } else if (sum < c) {
                i++;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(judgeSquareSum(4));
    }
}
