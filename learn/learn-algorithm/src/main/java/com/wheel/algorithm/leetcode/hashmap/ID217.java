package com.wheel.algorithm.leetcode.hashmap;

import java.util.HashMap;

/**
 * @desc {@link https://leetcode-cn.com/problems/contains-duplicate/description/}
 * @author: zhouf
 */
public class ID217 {

    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 1}));

        System.out.println(containsDuplicate(new int[]{1, 2, 3, 4}));

        System.out.println(containsDuplicate(new int[]{1,1,1,3,3,4,3,2,4,2}));
    }

    public static boolean containsDuplicate(int[] nums) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int num : nums) {
            hashMap.put(num, num);
        }
        return nums.length != hashMap.size();
    }
}
