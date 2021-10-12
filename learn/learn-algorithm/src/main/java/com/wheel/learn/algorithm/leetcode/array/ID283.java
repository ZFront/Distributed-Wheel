package com.wheel.learn.algorithm.leetcode.array;

import com.wheel.common.util.JsonUtil;

/**
 * @desc {@link https://leetcode-cn.com/problems/move-zeroes/description/}
 * @author: zhouf
 */
public class ID283 {

    public static void main(String[] args) {
        System.out.println(JsonUtil.toString(moveZeroes(new int[]{0, 1, 0, 3, 4})));
    }

    /**
     * 双指针
     */
    public static int[] moveZeroes(int[] nums) {
        int n = nums.length, left = 0, right = 0;
        while (right < n) {
            if (nums[right] != 0) {
                swap(nums, left, right);
                left++;
            }
            right++;
        }
        return nums;
    }

    private static void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
