package com.wheel.learn.algorithm.leetcode.sort;

import java.util.*;

/**
 * @desc {@link https://leetcode-cn.com/problems/sort-characters-by-frequency/description/}
 * @author: zhouf
 */
public class ID451 {

    public static void main(String[] args) {
        System.out.println(frequencySort("zncppyyyyc"));
    }

    // 直接排序, 采用Hash记录键值对
    public static String frequencySort(String s) {
        // 统计
        HashMap<Character, Integer> countMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
        // 根据统计的数量，直接排序
        List<Map.Entry<Character, Integer>> items = new ArrayList<>(countMap.entrySet());
        items.sort((a, b) -> b.getValue() - a.getValue());

        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> item : items) {
            char key = item.getKey();
            int val = item.getValue();
            for (int i = 0; i < val; i++) {
                result.append(key);
            }
        }
        return result.toString();
    }

    // 最大堆
    public String frequencySort2(String s) {
        HashMap<Character, Integer> countMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }

        // 创建最大堆
        PriorityQueue<Map.Entry<Character, Integer>> items = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        items.addAll(countMap.entrySet());

        StringBuilder result = new StringBuilder();
        while (!items.isEmpty()) {
            // 最大的值
            Map.Entry<Character, Integer> item = items.poll();
            char key = item.getKey();
            int value = item.getValue();
            for (int i = 0; i < value; i++) {
                result.append(key);
            }
        }

        return result.toString();
    }

}
