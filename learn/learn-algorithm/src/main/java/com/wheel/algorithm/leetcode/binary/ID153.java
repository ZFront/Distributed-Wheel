package com.wheel.algorithm.leetcode.binary;

/**
 * @desc {@link https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/description/}
 * @author: zhouf
 */
public class ID153 {

    public static void main(String[] args) {
        System.out.println(findMin(new int[]{3, 4, 5, 1, 2}));

        System.out.println(findMid2(new int[]{3, 4, 5, 1, 2}));
    }

    /**
     * 暴力解法
     *
     * @param nums
     * @return
     */
    public static int findMin(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > nums[i]) {
                return nums[i];
            }
        }
        return nums[0];
    }

    /**
     * 二分法
     *
     * @param nums
     * @return
     */
    public static int findMid2(int[] nums) {
        int l = 0, h = nums.length - 1;
        while (l < h) {
            int mid = l + (h - l) / 2;
            if (nums[mid] <= nums[h]) {
                h = mid;
            } else {
                l = mid + 1;
            }
        }
        return nums[l];
    }
}
