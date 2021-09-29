package com.wheel.algorithm.leetcode.binary;

import com.wheel.common.util.JsonUtil;

/**
 * @desc {@link https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/}
 * @author: zhouf
 */
public class ID34 {

    public static void main(String[] args) {
        System.out.println(JsonUtil.toString(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
    }

    /**
     * 二分查找
     * 可以用二分查找找出第一个位置和最后一个位置，但是寻找的方法有所不同，需要实现两个二分查找。
     * 我们将寻找 target 最后一个位置，转换成寻找 target+1 第一个位置，再往前移动一个位置。这样我们只需要实现一个二分查找代码即可。
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] searchRange(int[] nums, int target) {
        int first = binarySearch(nums, target);
        int last = binarySearch(nums, target + 1) - 1;
        if (first == nums.length || nums[first] != target) {
            return new int[]{-1, -1};
        } else {
            return new int[]{first, Math.max(first, last)};
        }
    }


    public static int binarySearch(int[] nums, int target) {
        int l = 0, h = nums.length;
        while (l < h) {
            int m = l + (h - l) / 2;
            if (nums[m] >= target) {
                h = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }
}
