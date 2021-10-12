package com.wheel.learn.algorithm.leetcode.sort;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @desc {@link https://leetcode-cn.com/problems/kth-largest-element-in-an-array/description/}
 * @author: zhouf
 */
public class ID215 {

    /**
     * 这个实现的方式有非常多。挑几个经典的来实现
     */

    /**
     * 排序：时间复杂度 O(NlogN)，空间复杂度 O(1)
     */
    public static int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * 堆：时间复杂度 O(NlogK)，空间复杂度 O(K)。
     */
    public static int findKthLargest2(int[] nums, int k) {
        // 小顶堆
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int val : nums) {
            pq.add(val);
            // 维护堆的大小为K
            if (pq.size() > k) {
                // 删除队首元素
                pq.poll();
            }
        }
        return pq.peek();
    }

    /**
     * 加餐，使用PriorityQueue实现大小堆
     */
    public void heapExtend() {
        //小顶堆，默认容量为11
        PriorityQueue<Integer> minPriorityQueue = new PriorityQueue<>();
        //大顶堆
        PriorityQueue<Integer> maxPriorityQueue = new PriorityQueue<>(11, (a, b) -> b - a);
    }

    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3, 1, 2, 5, 7, 8, 4, 6}, 2));
        System.out.println(findKthLargest2(new int[]{3, 1, 2, 5, 7, 8, 4, 6}, 2));
    }
}
