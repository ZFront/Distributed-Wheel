package com.wheel.basis.leetcode.pointer;

import com.wheel.common.util.JsonUtil;

/**
 * @desc {@link https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/}
 * @author: zhouf
 */
public class ID167 {

    public static int[] twoSum(int[] numbers, int target) {
        if (numbers == null) {
            return null;
        }
        // 末端指针
        int i = 0, j = numbers.length - 1;
        while (i < j) {
            int sum = numbers[i] + numbers[j];
            if (target == sum) {
                return new int[]{i, j};
            } else if (target < sum) {
                j--;
            } else {
                i++;
            }
        }
        return new int[]{};
    }


    public static void main(String[] args) {
        int target = 5;
        int[] numbers = {1, 2, 3, 7, 9};
        System.out.println(JsonUtil.toString(twoSum(numbers, target)));
    }
}
