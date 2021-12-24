package com.wheel.learn.algorithm.leetcode.hashmap;

import com.wheel.common.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc 两数之和
 * {@link https://leetcode-cn.com/problems/two-sum/description/}
 * @author: zhouf
 */
public class ID1 {

    public static void main(String[] args) {
        System.out.println(JsonUtil.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));

        System.out.println(JsonUtil.toString(twoSum(new int[]{2, 7, 11, 15}, 18)));

        System.out.println(JsonUtil.toString(twoSum(new int[]{3, 7, 11, 15}, 9)));
    }

    /**
     * 暴力解法
     */
    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = nums.length - 1; j > i; j--) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{};
    }

    /**
     * HashMap 算法
     */
    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }
}
