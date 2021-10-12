package com.wheel.learn.algorithm.leetcode.array;

import com.google.common.collect.Maps;

import java.util.HashMap;

/**
 * @desc {@link https://leetcode-cn.com/problems/find-the-duplicate-number/description/}
 * @author: zhouf
 */
public class ID287 {

    public static void main(String[] args) {
        System.out.println(findDuplicate(new int[]{1, 3, 4, 2, 2}));

        System.out.println(findDuplicate(new int[]{1, 3, 4, 2, 1}));
    }

    /**
     * Hash 解法
     *
     * @param nums
     * @return
     */
    public static int findDuplicate(int[] nums) {
        HashMap<Integer, Integer> map = Maps.newHashMap();
        for (int num : nums) {
            if (map.get(num) != null) {
                return num;
            }
            map.put(num, 1);
        }
        return 0;
    }
}
