package com.wheel.learn.algorithm.leetcode.array;

import com.wheel.common.util.JsonUtil;

import java.util.Arrays;

/**
 * @desc 错误的集合
 * {@link https://leetcode-cn.com/problems/set-mismatch/description/}
 * @author: zhouf
 */
public class ID645 {

    public static void main(String[] args) {
        System.out.println(JsonUtil.toString(findErrorNums(new int[]{1, 2, 2, 4})));

        System.out.println(JsonUtil.toString(findErrorNums(new int[]{1, 1})));

        System.out.println(JsonUtil.toString(findErrorNums(new int[]{1})));
    }

    /**
     *
     */
    public static int[] findErrorNums(int[] nums) {
        int[] errorNums = new int[2];
        int n = nums.length;
        Arrays.sort(nums);
        int prev = 0;
        for (int i = 0; i < n; i++) {
            int curr = nums[i];
            if (curr == prev) {
                errorNums[0] = prev;
            } else if (curr - prev > 1) {
                errorNums[1] = prev + 1;
            }
            prev = curr;
        }
        if (nums[n - 1] != n) {
            errorNums[1] = n;
        }
        return errorNums;
    }
}
