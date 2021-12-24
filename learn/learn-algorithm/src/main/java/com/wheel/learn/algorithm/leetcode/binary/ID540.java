package com.wheel.learn.algorithm.leetcode.binary;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc 有序数组中的单一元素
 * {@link https://leetcode-cn.com/problems/single-element-in-a-sorted-array/description/}
 * @author: zhouf
 */
public class ID540 {

    public static void main(String[] args) {
        System.out.println(singleNonDuplicate(new int[]{1, 1, 2, 3, 3, 4, 4, 7, 7}));

        System.out.println(singleNonDuplicate2(new int[]{1, 1, 2, 3, 3, 4, 4, 7, 7}));
    }

    /**
     * 常规解法，采用HashMap实现
     *
     * @param nums
     * @return
     */
    public static int singleNonDuplicate(int[] nums) {
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return 0;
    }

    /**
     * 暴力搜索
     *
     * @return
     */
    public static int singleNonDuplicate2(int[] nums) {
        for (int i = 0; i < nums.length - 1; i += 2) {
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        return nums[nums.length - 1];
    }


    /**
     * 二分法
     *
     * @param nums
     * @return
     */
    public static int singleNonDuplicate3(int[] nums) {
        // TODO
        return 2;
    }
}
