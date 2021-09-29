package com.wheel.algorithm.leetcode.array;

/**
 * @desc {@link https://leetcode-cn.com/problems/max-consecutive-ones/description/}
 * @author: zhouf
 */
public class ID485 {

    public static void main(String[] args) {
        System.out.println(findMaxConsecutiveOnes(new int[]{1, 0, 1, 1, 1, 0, 1}));
    }

    public static int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, cur = 0;
        for (int x : nums) {
            cur = x == 0 ? 0 : cur + 1;
            max = Math.max(max, cur);
        }
        return max;
    }
}
